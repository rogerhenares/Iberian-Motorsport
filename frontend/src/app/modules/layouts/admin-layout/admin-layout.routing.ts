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
import {LoginComponent} from "../../login/login.component";

export const AdminLayoutRoutes: Routes = [
    {
      path: '',
      children: [ {
        path: 'dashboard',
        component: DashboardComponent
    }]}, {
    path: '',
    children: [ {
      path: 'userprofile',
      component: UserProfileComponent
    }]
    }, {
      path: '',
      children: [ {
        path: 'icons',
        component: IconsComponent
        }]
    }, {
        path: '',
        children: [ {
            path: 'notifications',
            component: NotificationsComponent
        }]
    }, {
        path: '',
        children: [ {
            path: 'maps',
            component: MapsComponent
        }]
    }, {
        path: '',
        children: [ {
            path: 'typography',
            component: TypographyComponent
        }]
    }, {
        path: '',
        children: [ {
            path: 'upgrade',
            component: UpgradeComponent
        }]
    },
    {
        path: '',
        children: [ {
            path: 'championship',
            component: ChampionshipComponent
        }]
    },
    {
        path: '',
        children: [ {
            path: 'championship/new',
            component: ChampionshipFormComponent
        }]
    },
    { path: 'dashboard',      component: DashboardComponent },
    { path: 'user-profile',   component: UserProfileComponent },
    { path: 'table-list',     component: TableListComponent },
    { path: 'typography',     component: TypographyComponent },
    { path: 'icons',          component: IconsComponent },
    { path: 'maps',           component: MapsComponent },
    { path: 'notifications',  component: NotificationsComponent },
    { path: 'upgrade',        component: UpgradeComponent },
    { path: 'login',          component: LoginComponent },
    { path: 'championship',   component: ChampionshipComponent },
    { path: 'championship/new', component: ChampionshipFormComponent}
];
