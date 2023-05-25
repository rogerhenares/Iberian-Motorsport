import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ChampionshipComponent} from "./component/championship.component";
import {ChampionshipListComponent} from "./list.component/championship-list.component";

const routes: Routes = [
    { path: '', component: ChampionshipListComponent},
    { path: 'championship/:id', component: ChampionshipComponent },
    { path: 'new', component: ChampionshipComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ChampionshipRoutingModule {}
