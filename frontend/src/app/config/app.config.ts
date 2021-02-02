import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { IAppConfig } from './app-config.model';
import {UUID} from 'angular2-uuid';
@Injectable()
export class AppConfig {
  static settings: IAppConfig;
  static udid: string;
  constructor(private http: HttpClient) {}
  load() {
    let item = localStorage.getItem("UNIQUE_DEVICE_ID");
    if (item === undefined || item === null || item === ''){
      console.log('generating uuid')
      item = UUID.UUID();
      localStorage.setItem("UNIQUE_DEVICE_ID", item);
    }
    AppConfig.udid = item;
    const jsonFile = `assets/config/config.${environment.name}.json`;
    return new Promise<void>((resolve, reject) => {
      this.http.get(jsonFile).toPromise().then((response: IAppConfig) => {
        AppConfig.settings = <IAppConfig> response;
        resolve();
      }).catch((response: any) => {
        reject(`Could not load file '${jsonFile}': ${JSON.stringify(response)}`);
      });
    });
  }
}
