import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './login/login.component';
import {IndexComponent} from './index/index.component';

const routes: Routes = [
  {path: "login", component: LoginComponent},
  {path: "index", component: IndexComponent},
  {path: "", pathMatch: "full", redirectTo: "login"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
