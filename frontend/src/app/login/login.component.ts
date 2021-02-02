import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AppConfig} from '../config/app.config';
import {ApiResult} from '../model/api.result';
import {CaptchaResponse} from '../model/captcha.response';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  title = AppConfig.settings.system.title;
  captchaPath: string;

  constructor(private http: HttpClient) {
  }

  username = new FormControl('', [Validators.required]);
  password = new FormControl('', [Validators.required, Validators.minLength(6)]);
  validateCode = new FormControl('', [Validators.required, Validators.minLength(4)])
  loginForm = new FormGroup({
    username: this.username,
    password: this.password,
    code: this.validateCode
  })

  ngOnInit(): void {
    this.loginForm.statusChanges.subscribe(status => {

    })
    this.getCaptcha()
  }

  onSubmit(){
    console.log('form, ', this.loginForm.value)
  }

  getCaptcha() {
    this.http.get("captcha")
      .subscribe(res => {
        console.log('res', res)
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

}
