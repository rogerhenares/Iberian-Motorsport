import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import {ChampionshipRoutingModule} from "./championship-routing.module";
import {ChampionshipComponent} from "./component/championship.component";
import {ChampionshipListComponent} from "./list.component/championship-list.component";

@NgModule({
    imports: [
        CommonModule,
        ReactiveFormsModule,
        ChampionshipRoutingModule,
        TranslateModule,
        SweetAlert2Module,
    ],
    declarations: [
        ChampionshipComponent,
        ChampionshipListComponent
    ]
})
export class ChampionshipModule {}
