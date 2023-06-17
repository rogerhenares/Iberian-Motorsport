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

export const AdminLayoutRoutes: Routes = [

    { path: 'dashboard',      component: DashboardComponent },
    { path: 'user-profile',   component: UserProfileComponent },
    { path: 'table-list',     component: TableListComponent },
    { path: 'typography',     component: TypographyComponent },
    { path: 'icons',          component: IconsComponent },
    { path: 'maps',           component: MapsComponent },
    { path: 'notifications',  component: NotificationsComponent },
    { path: 'upgrade',        component: UpgradeComponent },
    { path: 'championship',   component: ChampionshipComponent },
    { path: 'championship/new', component: ChampionshipFormComponent },
    { path: 'race-rules/new', component: RaceRulesFormComponent },
    { path: 'session/new',    component: SessionFormComponent },
    { path: 'race/new',       component: RaceFormComponent}
];
