import {Inject, Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AppConfig} from '../config/app.config';

@Injectable()
export class AppIntercepter implements HttpInterceptor{

  constructor() {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const url = req.url;
    console.log('url', url)
    req.headers.append("UDID", AppConfig.udid);
    console.log('headers', req.headers)
    if (!url.startsWith("assets/")){
      const baseUrl = AppConfig.settings.apiServer.baseUrl;
      const httpRequest = req.clone({
        url: `${baseUrl}/${req.url}`,
        headers: req.headers
          .set("UDID", AppConfig.udid)
      });
      console.log('base_url - request', httpRequest)
      return next.handle(httpRequest);
    }
    console.log('request', req)
    return next.handle(req);
  }
}
