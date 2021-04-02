import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AppConfig} from '../config/app.config';
import {ApiResult} from '../model/api.result';
import {CaptchaResponse} from '../model/captcha.response';
import {MatToolbar} from "@angular/material/toolbar";
import {MatProgressBar} from "@angular/material/progress-bar";
import {MatButton} from "@angular/material/button";

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

  constructor(private http: HttpClient) {
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
    this.setViewsEnabled(this.loading);
    if (this.loginForm.valid){
      this.http.post("system/login", this.loginForm.value)
        .subscribe(result => {
          console.log('logresult:', result)
          this.loading = false;
          this.setViewsEnabled(this.loading);
        }, throwable => {
          this.username.setErrors(Validators.required)
          this.loading = false;
          this.setViewsEnabled(this.loading);
        });
    }
  }

  setViewsEnabled(loading: boolean){
    if (loading){
      this.username.disable({onlySelf: true, emitEvent: false});
      this.password.disable({onlySelf: true, emitEvent: false});
      this.validateCode.disable({onlySelf: true, emitEvent: false});
    }else{
      this.username.enable({onlySelf: true, emitEvent: false});
      this.password.enable({onlySelf: true, emitEvent: false});
      this.validateCode.enable({onlySelf: true, emitEvent: false});
    }
    this.captcha.nativeElement.disabled = loading;
    this.submit.disabled = loading;
  }

  getCaptcha() {
    this.http.get("captcha?primary=" + this.primaryColor)
      .subscribe(res => {
        const response = res as ApiResult<CaptchaResponse>;
        if (response.data.enabled === true){
          this.captchaPath = 'data:image/png;base64,' + response.data.image;
        }else{
          this.validateCode.clearValidators();
          this.loginForm.removeControl("code")
        }
      }, error => {
        this.captchaPath = ''
        console.log('error', error)
      })
  }

  ngAfterViewInit(): void {
    this.submit.disabled = true;
    this.primaryColor = getComputedStyle(this.primary.nativeElement).backgroundColor;
    this.getCaptcha()
  }

}
