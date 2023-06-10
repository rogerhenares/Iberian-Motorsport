import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { AppRoutingModule } from './app.routing';
import { ComponentsModule } from '../components/components.module';
import { AppComponent } from './component/app.component';
import { AdminLayoutComponent } from '../layouts/admin-layout/admin-layout.component';
import { UserAdminService } from "../../service/user-admin.service";
import { UserService } from "../../service/user.service";
import { ProcessService } from "../../service/process.service";
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import { AuthenticationInterceptor } from "../../authentication/authentication.interceptor";
import {AppContext} from "../../util/AppContext";
import {ChampionshipService} from "../../service/championship.service";
import {AuthenticationService} from "../../service/authentication.service";

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
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthenticationInterceptor,
      multi: true
    },
    AppContext,
    UserAdminService,
    AuthenticationService,
    UserService,
    ChampionshipService,
    ProcessService],
  bootstrap: [AppComponent]
})
export class AppModule { }
