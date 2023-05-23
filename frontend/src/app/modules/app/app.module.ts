import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { AppRoutingModule } from './app.routing';
import { ComponentsModule } from '../components/components.module';
import { AppComponent } from './component/app.component';
import { AdminLayoutComponent } from '../layouts/admin-layout/admin-layout.component';
import {UserAdminService} from "../../service/user-admin.service";
import {UserService} from "../../service/user.service";
import {ProcessService} from "../../service/process.service";
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';

@NgModule({
  imports: [
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    ComponentsModule,
    RouterModule,
    AppRoutingModule,
    SweetAlert2Module.forRoot()
  ],
  declarations: [
    AppComponent,
    AdminLayoutComponent,
  ],
  providers: [
      /**
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthenticationInterceptor,
      multi: true
    },
       */
    UserAdminService,
    UserService,
    ProcessService],
  bootstrap: [AppComponent]
})
export class AppModule { }