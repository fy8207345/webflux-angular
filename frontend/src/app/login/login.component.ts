import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AppConfig} from '../config/app.config';
import {ApiResult} from '../model/api.result';
import {CaptchaResponse} from '../model/captcha.response';
import {MatProgressBar} from "@angular/material/progress-bar";
import {MatButton} from "@angular/material/button";
import {LoginResponse} from "../model/login.response";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatFormField} from "@angular/material/form-field";
import {map} from "rxjs/operators";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, AfterViewInit {
  title = AppConfig.settings.system.title;
  captchaPath: string;
  primaryColor: string;
  loading = false;

  @ViewChild('progress') progress: MatProgressBar;
  @ViewChild('primary') primary: ElementRef;
  @ViewChild('captcha') captcha: ElementRef;
  @ViewChild('submit') submit: MatButton;
  @ViewChild('usernamefield') usernameFiled: MatFormField;
  @ViewChild('passwordfield') passwordField: MatFormField;

  constructor(private _http: HttpClient, private _router: Router, private _snackBar: MatSnackBar) {
  }

  username = new FormControl('', [Validators.required]);
  password = new FormControl('', [Validators.required, Validators.minLength(6)]);
  validateCode = new FormControl('', [Validators.required, Validators.minLength(1)])
  loginForm = new FormGroup({
    username: this.username,
    password: this.password,
    code: this.validateCode
  })

  ngOnInit(): void {

    this.loginForm.statusChanges.subscribe(status => {
        this.submit.disabled = (status !== 'VALID');
    })
  }

  onSubmit(){
    console.log('form, ', this.loginForm.value)
    this.loading = true;
    if(this.loginForm.valid){
      this._http.post("system/login", this.loginForm.value)
        .pipe(map(res => new ApiResult<LoginResponse>(res)))
        .subscribe(response => {
          this.loading = false;
          if (response.success()){
            this._router.navigateByUrl("index");
          } else if(response.code === 1001){
            this.validateCode.setErrors({msg: response.msg});
          } else{
            this._snackBar.open(response.msg, null, {duration: 3000});
          }
          console.log('logresult:', response)
        }, throwable => {
          console.log('error ', throwable)
          this._snackBar.open(throwable);
          this.loading = false;
        });
    }
  }


  getCaptcha() {
    this._http.get<ApiResult<CaptchaResponse>>("captcha?primary=" + this.primaryColor)
      .pipe(map(res => new ApiResult<CaptchaResponse>(res)))
      .subscribe(res => {
        // const response = res as ApiResult<CaptchaResponse>;
        if (res.data.enabled === true){
          this.captchaPath = 'data:image/png;base64,' + res.data.image;
        }else{
          this.validateCode.clearValidators();
          this.loginForm.removeControl("code")
        }
      }, error => {
        this.captchaPath = ''
        console.log('error', error)
      })
  }

  submitDiabled(): boolean{
    if(this.loading){
      return true;
    }
    return !this.loginForm.valid;
  }

  formDisabled(): any{
    if(this.loading){
      return true;
    }
    return null;
  }

  ngAfterViewInit(): void {
    this.primaryColor = getComputedStyle(this.primary.nativeElement).backgroundColor;
    this.getCaptcha()
  }

}
