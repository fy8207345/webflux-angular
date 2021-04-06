import { NgModule } from '@angular/core';
import {Routes, RouterModule, Router, Route, ActivatedRoute} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {HomeComponent} from './home/home.component';
import {AuthGuardService} from "./guards/auth.guard.service";
import {Constants} from "./constants";
import {AuthService} from "./service/auth.service";
import {MatSnackBar} from "@angular/material/snack-bar";

const routes: Routes = [
  {path: Constants.LOGIN_PATH, component: LoginComponent, },
  {path: Constants.HOME_PATH, component: HomeComponent, canActivate: [AuthGuardService]},
  {path: "", redirectTo: Constants.LOGIN_PATH, pathMatch: "full"},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
  constructor(private router: Router, private authService: AuthService, private _snackBar: MatSnackBar, private route: ActivatedRoute) {
    this.router.errorHandler = (error: any) => {
      console.log('error', error)
      const path = (this.authService.isLoggedIn() ? Constants.HOME_PATH : Constants.LOGIN_PATH)
      const action = (this.authService.isLoggedIn() ? "跳到首页" : "跳到登录页");
      this._snackBar.open("找不到指定的路径 : " , action, {duration: -1})
        .onAction()
        .subscribe(value => {
         router.navigate([path]);
        })
    }
  }
}
