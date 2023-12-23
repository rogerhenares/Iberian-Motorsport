import { Component } from "@angular/core";
import { Championship } from "../../model/Championship";
import { ChampionshipService } from "../../service/championship.service";
import { Router } from "@angular/router";
import {AppContext} from "../../util/AppContext";
import {CriteriaChampionship} from "../../model/CriteriaChampionship";

@Component({
    selector: 'app-championship-list',
    templateUrl: 'championship-list.component.html',
    styleUrls: ['championship-list.component.css']
})
export class ChampionshipListComponent {

    currentChampionshipsOpen = true;
    upcomingChampionshipsOpen = false;
    pastChampionshipsOpen = false;
    disabledChampionshipsOpen = false;
    totalPages: number = 0;
    criteria: CriteriaChampionship = new CriteriaChampionship();


    constructor(
        private championshipService: ChampionshipService,
        public router: Router,
        public appContext : AppContext
    ) { }

    ngOnInit(): void {

    }

    selectChampionship(championshipId: number) {
        this.router.navigate(['/championship/', championshipId])
    }


    onSelectedChampionship(selectedChampionship: Championship) {
        this.router.navigate(['/championship/', selectedChampionship.id])

    }
}
