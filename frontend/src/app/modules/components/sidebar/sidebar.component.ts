import { Component, OnInit } from '@angular/core';
import {AppContext} from "../../../util/AppContext";

declare const $: any;
declare interface RouteInfo {
    path: string;
    title: string;
    icon: string;
    class: string;
    logged: boolean;
}
export const ROUTES: RouteInfo[] = [
    { path: '/dashboard', title: 'Home',  icon: 'dashboard', class: '', logged: true },
    { path: '/championship', title: 'Championships',  icon:'content_paste', class: '', logged: false },
    { path: '/championship', title: 'Championships',  icon:'content_paste', class: '', logged: true },
    { path: '/user-profile', title: 'Profile',  icon:'person', class: '', logged: true },
    { path: '/login', title: 'Login', icon:'person', class: '', logged: false}
];

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  menuItems: any[];

  constructor(
      public appContext: AppContext
  ) { }

  ngOnInit() {
    this.menuItems = ROUTES.filter(menuItem => menuItem);
  }
  isMobileMenu() {
      if ($(window).width() > 991) {
          return false;
      }
      return true;
  };
}
