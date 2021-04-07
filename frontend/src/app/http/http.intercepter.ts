import {Injectable} from '@angular/core';
import {HttpEvent, HttpEventType, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AppConfig} from '../config/app.config';
import {AuthService} from "../service/auth.service";
import {map} from "rxjs/operators";

@Injectable()
export class AppIntercepter implements HttpInterceptor{

  constructor(private authService: AuthService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const url = req.url;
    req.headers.set("UDID", AppConfig.udid);
    if(this.authService.isLoggedIn()){
      req.headers.set("Authorization", "Bearer " + this.authService.token.token);
    }
    if (!url.startsWith("assets/")){
      const baseUrl = AppConfig.settings.apiServer.baseUrl;
      const httpRequest = req.clone({
        url: `${baseUrl}/${req.url}`,
        headers: req.headers
          .set("UDID", AppConfig.udid)
          .set("Authorization", "Bearer " + this.authService.token?.token)
      });
      console.log('base_url - request', httpRequest)
      return next.handle(httpRequest).pipe(map(event => {
        if(event.type === HttpEventType.Response){
          console.log('response', event.headers.keys())
        }
        return event;
      }));
    }
    console.log('request', req)
    return next.handle(req);
  }
}
