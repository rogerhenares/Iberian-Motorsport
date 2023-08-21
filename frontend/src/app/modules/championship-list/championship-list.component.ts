import {ChangeDetectorRef, Component, ViewChild} from "@angular/core";
import { Championship } from "../../model/Championship";
import { Pageable } from "../../model/Pageable";
import { ChampionshipService } from "../../service/championship.service";
import { Router } from "@angular/router";
import {AppContext} from "../../util/AppContext";
import {Race} from "../../model/Race";
import {CriteriaChampionship} from "../../model/CriteriaChampionship";
import {ChampionshipComponent} from "../championship/championship.component";

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

    goToPreviousPage(pageable: any, championships: Championship[]) {
        if (pageable.page > 0) {
            pageable.page--;
            this.getChampionships(pageable).subscribe(fetchedChampionships => {
                championships.length = 0;
                championships.push(...fetchedChampionships);
            });
        }
    }

    goToNextPage(pageable: any, championships: Championship[]) {
        if (pageable.page < this.totalPages - 1) {
            pageable.page++;
            this.getChampionships(pageable).subscribe(fetchedChampionships => {
                championships.length = 0;
                championships.push(...fetchedChampionships);
            });
        }
    }


    onSelectedChampionship(selectedChampionship: Championship) {
        this.router.navigate(['/championship/', selectedChampionship.id])

    }
}
