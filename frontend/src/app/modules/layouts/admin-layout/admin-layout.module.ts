import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AdminLayoutRoutes } from './admin-layout.routing';
import { UserProfileComponent } from '../../user-profile/user-profile.component';
import { TableListComponent } from '../../table-list/table-list.component';
import { NotificationsComponent } from '../../notifications/notifications.component';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatNativeDateModule, MatRippleModule} from '@angular/material/core';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatSelectModule} from '@angular/material/select';
import {LoginComponent} from "../../login/login.component";
import {HomeComponent} from "../../home/home.component";
import {ChampionshipFormComponent} from "../../championship-form/championship-form.component";
import {RaceRulesFormComponent} from "../../racerules-form/race-rules-form.component";
import {MatIconModule} from "@angular/material/icon";
import {SessionFormComponent} from "../../session-form/session-form.component";
import {SweetAlert2Module} from "@sweetalert2/ngx-sweetalert2";
import {TranslateModule} from "@ngx-translate/core";
import {RaceFormComponent} from "../../race-form/race-form.component";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatStepperModule} from "@angular/material/stepper";
import {NewRace} from "../../new-race/new-race";
import {ChampionshipListComponent} from "../../championship-list/championship-list.component";
import {ChampionshipDetailsComponent} from "../../championship-details/championship-details.component";
import {CollapsibleSectionComponent} from "../../collapsible-section/collapsible-section.component";
import {RaceInfoComponent} from "../../race-info/race-info.component";
import {StandingsComponent} from "../../standings/standings.component";
import {ResultsComponent} from "../../results/results.component";
import {MatTabsModule} from "@angular/material/tabs";
import {JoinChampionshipComponent} from "../../join-championship/join-championship.component";
import {ChampionshipComponent} from "../../championship/championship.component";
import {RaceListComponent} from "../../race-list/race-list.component";
import {SanctionsComponent} from "../../sanctions/sanctions.component";
import {SanctionFormComponent} from "../../sanction-form/sanction-form.component";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {UserListComponent} from "../../user-list/user-list.component";
import {MatButtonToggleModule} from "@angular/material/button-toggle";
import {MatExpansionModule} from "@angular/material/expansion";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {BopFormComponent} from "../../bop-form/bop-form.component";


@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(AdminLayoutRoutes),
        FormsModule,
        ReactiveFormsModule,
        MatButtonModule,
        MatRippleModule,
        MatFormFieldModule,
        MatInputModule,
        MatSelectModule,
        MatTooltipModule,
        NgOptimizedImage,
        MatIconModule,
        SweetAlert2Module,
        TranslateModule,
        MatCheckboxModule,
        MatStepperModule,
        MatTabsModule,
        MatProgressSpinnerModule,
        MatButtonToggleModule,
        MatExpansionModule,
        MatDatepickerModule,
        MatInputModule,
        MatNativeDateModule
    ],
  declarations: [
    LoginComponent,
    UserProfileComponent,
    TableListComponent,
    NotificationsComponent,
    ChampionshipComponent,
    HomeComponent,
    ChampionshipFormComponent,
    RaceRulesFormComponent,
    SessionFormComponent,
    RaceFormComponent,
    NewRace,
    ChampionshipListComponent,
    ChampionshipDetailsComponent,
    CollapsibleSectionComponent,
    RaceInfoComponent,
    StandingsComponent,
    ResultsComponent,
    JoinChampionshipComponent,
    RaceListComponent,
    SanctionsComponent,
    SanctionFormComponent,
    UserListComponent,
    BopFormComponent
  ]
})

export class AdminLayoutModule {}
