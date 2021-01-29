import { Component } from '@angular/core';
import {AppConfig} from './config/app.config';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = AppConfig.settings.system.title;
}
