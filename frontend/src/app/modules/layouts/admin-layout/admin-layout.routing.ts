import { Routes } from '@angular/router';

import { DashboardComponent } from '../../dashboard/dashboard.component';
import { UserProfileComponent } from '../../user-profile/user-profile.component';
import { TableListComponent } from '../../table-list/table-list.component';
import { TypographyComponent } from '../../typography/typography.component';
import { IconsComponent } from '../../icons/icons.component';
import { MapsComponent } from '../../maps/maps.component';
import { NotificationsComponent } from '../../notifications/notifications.component';
import { UpgradeComponent } from '../../upgrade/upgrade.component';
import {ChampionshipComponent} from "../../championship/championship.component";
import {ChampionshipFormComponent} from "../../championship-form/championship-form.component";
import {RaceRulesFormComponent} from "../../racerules-form/race-rules-form.component";
import {SessionFormComponent} from "../../session-form/session-form.component";
import {RaceFormComponent} from "../../race-form/race-form.component";
import {LoginComponent} from "../../login/login.component";
import {NewRace} from "../../new-race/new-race";
import {ChampionshipListComponent} from "../../championship-list/championship-list.component";
import {ChampionshipDetailsComponent} from "../../championship-details/championship-details.component";
import {RaceInfoComponent} from "../../race-info/race-info.component";

export const AdminLayoutRoutes: Routes = [

    { path: 'dashboard-feo',  component: DashboardComponent },
    { path: 'user-profile',   component: UserProfileComponent },
    { path: 'table-list',     component: TableListComponent },
    { path: 'typography',     component: TypographyComponent },
    { path: 'icons',          component: IconsComponent },
    { path: 'maps',           component: MapsComponent },
    { path: 'notifications',  component: NotificationsComponent },
    { path: 'upgrade',        component: UpgradeComponent },
    { path: 'login',          component: LoginComponent },
    { path: 'dashboard',      component: ChampionshipComponent },
    { path: 'championship/new', component: ChampionshipFormComponent },
    { path: 'championship',    component: ChampionshipListComponent},
    { path: 'championship/:championshipId', component: ChampionshipDetailsComponent},
    { path: 'race-rules/form', component: RaceRulesFormComponent },
    { path: 'session/form',    component: SessionFormComponent },
    { path: 'race/form',       component: RaceFormComponent},
    { path: 'race/new',        component: NewRace}
];
