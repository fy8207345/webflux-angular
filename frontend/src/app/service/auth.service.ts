import {Injectable} from "@angular/core";
import {JwtToken} from "../model/jwt.token";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";
import {Constants} from "../constants";

@Injectable({
  providedIn: "root"
})
export class AuthService{

  private static TOKEN_KEY = "token";

  private _userInfo: any = null;
  private _token: JwtToken = null;

  set userInfo(userInfo: any){
    this._userInfo = userInfo;
  }

  get userInfo(){
    return this._userInfo;
  }

  set token(token: JwtToken){
    this._token = token;
    localStorage.setItem(AuthService.TOKEN_KEY, JSON.stringify(this._token));
  }

  get token(): JwtToken{
    return this._token;
  }

  logout(){
    this._token = null;
    localStorage.removeItem(AuthService.TOKEN_KEY);
  }

  public isLoggedIn(): boolean{
    if(this._token === null){
      return false;
    }
    if(this._token.expireTime.getTime() < new Date().getTime()){
      this.snackBar.open("登录信息已过期", "重新登录")
        .onAction()
        .subscribe(value => {
          this.router.navigate([Constants.LOGIN_PATH]);
        })
      return false;
    }
    return true;
  }

  constructor(private snackBar: MatSnackBar, private router: Router) {
    const value = localStorage.getItem(AuthService.TOKEN_KEY);
    if(value){
      this._token = new JwtToken(JSON.parse(value));
    }
  }
}
