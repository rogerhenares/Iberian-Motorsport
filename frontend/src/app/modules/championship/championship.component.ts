import {Component, NgModule, OnInit} from '@angular/core';
import {ChampionshipService} from "../../service/championship.service";
import {Championship} from "../../model/Championship";
import {Pageable} from "../../model/Pageable";
import {ActivatedRoute, Router} from '@angular/router';



@Component({
    selector: 'app-championship',
    templateUrl: './championship.component.html',
    styleUrls: ['./championship.component.css']
})
export class ChampionshipComponent implements OnInit {
    championships: Championship[];
    currentPage: number = 0;
    championshipsPerPage: number = 3;
    totalPages: number = 0;
    pageable: Pageable = new Pageable(0,3);
    selectedChampionshipId: number | null = null;


    constructor(
        private championshipService: ChampionshipService,
        public router: Router
    ) {}

    ngOnInit(): void {
        this.getChampionshipList(this.pageable.page);
    }

    getChampionshipList(page: Number): void {
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
        this.selectedChampionshipId = championshipId;
    }

    goToPreviousPage() {
        if (this.pageable.page >= 1) {
            this.pageable.page = this.pageable.page.valueOf() - 1;
            this.getChampionshipList(this.pageable.page);
        }
    }

    goToNextPage() {
        if (this.pageable.page < this.totalPages) {
            this.pageable.page = this.pageable.page.valueOf() + 1;
            this.getChampionshipList(this.pageable.page);
        } else {
        }
    }

    createNewChampionship() {
        this.router.navigateByUrl("championship/new");
    }

    createNewRace() {
        this.router.navigateByUrl("race/new");
    }
}

