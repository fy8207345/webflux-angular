import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AppConfig} from '../config/app.config';
import {ApiResult} from '../model/api.result';
import {CaptchaResponse} from '../model/captcha.response';
import {MatToolbar} from "@angular/material/toolbar";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, AfterViewInit {
  title = AppConfig.settings.system.title;
  captchaPath: string;
  primaryColor: string;

  @ViewChild('primary') primary: ElementRef;

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

    })

  }

  onSubmit(){
    console.log('form, ', this.loginForm.value)
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
    this.primaryColor = getComputedStyle(this.primary.nativeElement).backgroundColor;
    this.getCaptcha()
  }

}
