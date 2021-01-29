import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AppConfig} from '../config/app.config';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  title = AppConfig.settings.system.title;

  constructor(private http: HttpClient) { }

  username = new FormControl('', [Validators.required]);
  password = new FormControl('', [Validators.required, Validators.minLength(6)]);
  loginForm = new FormGroup({
    username: this.username,
    password: this.password,
  })

  ngOnInit(): void {
    this.loginForm.statusChanges.subscribe(status => {

    })
  }

  onSubmit(){
    console.log('form, ', this.loginForm.value)
  }
}
