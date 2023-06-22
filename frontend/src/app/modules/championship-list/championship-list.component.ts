import { Component, ViewChild } from "@angular/core";
import { Championship } from "../../model/Championship";
import { Pageable } from "../../model/Pageable";
import { ChampionshipService } from "../../service/championship.service";
import { Router } from "@angular/router";
import { ChampionshipComponent } from "../championship/championship.component";

@Component({
    selector: 'app-championship-list',
    templateUrl: 'championship-list.component.html',
    styleUrls: ['championship-list.component.css']
})

export class ChampionshipListComponent {


    championships: Championship[] = []
    championshipsPerPage: number = 3;
    currentPage: number = 0;
    totalPages: number = 0;
    pageable: Pageable = new Pageable(0, 3);
    selectedChampionshipId: number;

    constructor(
        private championshipService: ChampionshipService,
        public router: Router
    ) { }

    ngOnInit(): void {
        this.getChampionships(this.pageable.page);
    }

    getChampionships(page: Number): void {
        this.pageable.page = page;
        this.championshipService.getChampionshipList(this.pageable).subscribe(
            (response: any) => {
                this.championships = response.content;
                this.totalPages = response.totalPages.valueOf() -1;
            },
            (error: any) => {
                console.error('Failed to fetch championships', error);
            }
        );
    }

    selectChampionship(championshipId) {
        this.router.navigate(['/championship/', championshipId])
    }

    goToPreviousPage() {
        if (this.pageable.page >= 1) {
            this.pageable.page = this.pageable.page.valueOf() - 1;
            this.getChampionships(this.pageable.page);
        }
    }

    goToNextPage() {
        if (this.pageable.page < this.totalPages) {
            this.pageable.page = this.pageable.page.valueOf() + 1;
            this.getChampionships(this.pageable.page);
        } else {
        }
    }

    createNewChampionship() {
        this.router.navigateByUrl("championship/new");
    }

    goToChampionship(championshipId) {
        this.router.navigateByUrl("championship/" + championshipId)
    }
}
