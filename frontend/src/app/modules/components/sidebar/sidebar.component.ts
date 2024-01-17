import { Component, OnInit } from '@angular/core';
import {AppContext} from "../../../util/AppContext";

declare const $: any;
declare interface RouteInfo {
    path: string;
    title: string;
    icon: string;
    class: string;
    logged: boolean;
    adminOnly: boolean;
}
export const ROUTES: RouteInfo[] = [
    { path: '/dashboard', title: 'Dashboard',  icon: 'dashboard', class: '', logged: true, adminOnly: false },
    { path: '/championship', title: 'Championships',  icon:'content_paste', class: '', logged: false, adminOnly: false },
    { path: '/championship', title: 'Championships',  icon:'content_paste', class: '', logged: true, adminOnly: false },
    { path: '/user-profile', title: 'Profile',  icon:'person', class: '', logged: true, adminOnly: false },
    { path: '/login', title: 'Login', icon:'person', class: '', logged: false, adminOnly: false},
    { path: '/user-list', title: 'Users', icon:'perm_contact_calendar', class: '', logged: true, adminOnly: true },
    { path: '/summary', title: 'IML',  icon:'dashboard', class: '', logged: false, adminOnly: false },
    { path: '/summary', title: 'IML',  icon:'dashboard', class: '', logged: true, adminOnly: false },
    { path: '/esports', title: 'IML Esports', icon: 'videogame_asset', class: '', logged: false, adminOnly: false}
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
