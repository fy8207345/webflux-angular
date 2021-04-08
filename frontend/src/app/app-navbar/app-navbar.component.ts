import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-navbar',
  templateUrl: './app-navbar.component.html',
  styleUrls: ['./app-navbar.component.scss']
})
export class AppNavbarComponent implements OnInit {

  menus: [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get("system/menus")
      .subscribe(res => {

      })
  }

}
