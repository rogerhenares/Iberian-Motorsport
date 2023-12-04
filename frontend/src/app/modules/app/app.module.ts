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
import {DatePipe} from "@angular/common";
import {TimezoneService} from "../../service/timezone.service";
import {GridService} from "../../service/grid.service";
import {GridRaceService} from "../../service/gridrace.service";
import {SanctionService} from "../../service/sanction.service";
import {CarService} from "../../service/car.service";
import {ChampionshipCategoryService} from "../../service/championshipcategory.service";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {ImportService} from "../../service/import.service";
import {ExportService} from "../../service/export.service";
import {MatTooltipModule} from "@angular/material/tooltip";
import {SummaryComponent} from "../summary/summary.component";

@NgModule({
  imports: [
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    ComponentsModule,
    RouterModule,
    AppRoutingModule,
    SweetAlert2Module.forRoot({provideSwal: () => import('sweetalert2').then(({default: swal}) => swal.mixin({
        background: "#333333",
        cancelButtonColor: "#00FFC0",
        confirmButtonColor: "red",
        customClass: {
          container: 'custom-swal-container',
          popup: 'custom-swal-popup',
          title: 'custom-swal-title',
          content: 'custom-swal-content',
        }
      }))}),
    LanguageTranslationModule,
    MatProgressSpinnerModule,
    MatTooltipModule
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
    RaceService,
    DatePipe,
    TimezoneService,
    GridService,
    GridRaceService,
    SanctionService,
    CarService,
    ChampionshipCategoryService,
    ImportService,
    ExportService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
