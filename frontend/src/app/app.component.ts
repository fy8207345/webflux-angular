import {Component, OnInit} from '@angular/core';
import {AppConfig} from './config/app.config';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title: string;

  ngOnInit(): void {
    this.title = AppConfig.settings.system.title;
  }
}
