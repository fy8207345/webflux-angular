import {Injectable} from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateChild,
  Router,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthService} from "../service/auth.service";
import {Constants} from "../constants";

@Injectable()
export class AuthGuardService implements CanActivate, CanActivateChild{

  constructor(private _router: Router, private authService: AuthService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if(this.authService.token === null){
      console.log('not logged in, redirect to login page!')
      this._router.navigate([Constants.LOGIN_PATH]);
      return false;
    }
    return true;
  }

  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if(this.authService.token === null){
      console.log('not logged in, redirect to login page!')
      this._router.navigate([Constants.LOGIN_PATH]);
      return false;
    }
    return true;
  }

}
