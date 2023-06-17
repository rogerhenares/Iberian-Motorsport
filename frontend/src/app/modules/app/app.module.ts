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
import {ChampionshipService} from "../../service/championship.service";
import {ProcessService} from "../../service/process.service";
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import {RaceRulesService} from "../../service/racerules.service";
import {SessionService} from "../../service/session.service";
import {LanguageTranslationModule} from "../../util/language-translation.module";
import {RaceService} from "../../service/race.service";
import {AppContext} from "../../util/AppContext";
import {AuthenticationService} from "../../service/authentication.service";
import {AuthenticationInterceptor} from "../../authentication/authentication.interceptor";

@NgModule({
  imports: [
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    ComponentsModule,
    RouterModule,
    AppRoutingModule,
    SweetAlert2Module.forRoot(),
    LanguageTranslationModule
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
    RaceRulesService,
    ProcessService,
    ChampionshipService,
    SessionService,
    RaceService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
