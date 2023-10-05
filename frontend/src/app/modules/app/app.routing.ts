import { NgModule } from '@angular/core';
import { CommonModule, } from '@angular/common';
import { BrowserModule  } from '@angular/platform-browser';
import { Routes, RouterModule } from '@angular/router';

import { AdminLayoutComponent } from '../layouts/admin-layout/admin-layout.component';
import {AppContext} from "../../util/AppContext";

const routes: Routes =[
  { path: '', canActivate: [AppContext], pathMatch: 'full'},
  { path: '', component: AdminLayoutComponent, children:
        [{path: '', loadChildren: () =>
              import('../layouts/admin-layout/admin-layout.module').then(m => m.AdminLayoutModule)}]
  }
];

@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule.forRoot(routes,{
       useHash: false,
       onSameUrlNavigation: 'reload'
    })
  ],
  exports: [
  ],
})
export class AppRoutingModule { }
