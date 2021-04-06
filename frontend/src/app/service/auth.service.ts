import {Injectable} from "@angular/core";

@Injectable({
  providedIn: "root"
})
export class AuthService{

  private static TOKEN_KEY = "token";

  private _userInfo: any = null;
  private _token: string = null;

  set userInfo(userInfo: any){
    this._userInfo = userInfo;
  }

  get userInfo(){
    return this._userInfo;
  }

  set token(token: string){
    this._token = token;
    localStorage.setItem(AuthService.TOKEN_KEY, token);
  }

  get token(){
    return this._token;
  }

  logout(){
    this._token = null;
    localStorage.removeItem(AuthService.TOKEN_KEY);
  }

  constructor() {
    this.token = localStorage.getItem(AuthService.TOKEN_KEY);
  }
}
