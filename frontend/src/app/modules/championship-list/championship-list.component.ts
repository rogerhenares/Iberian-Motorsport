import {ChangeDetectorRef, Component, ViewChild} from "@angular/core";
import { Championship } from "../../model/Championship";
import { Pageable } from "../../model/Pageable";
import { ChampionshipService } from "../../service/championship.service";
import { Router } from "@angular/router";
import { ChampionshipComponent } from "../championship/championship.component";
import {map, Observable, of} from "rxjs";
import {catchError} from "rxjs/operators";

@Component({
    selector: 'app-championship-list',
    templateUrl: 'championship-list.component.html',
    styleUrls: ['championship-list.component.css']
})

export class ChampionshipListComponent {

    championships: Championship[] = [];
    currentChampionships: Championship[] = [];
    upcomingChampionships: Championship[] = [];
    pastChampionships: Championship[] = [];
    currentChampionshipsOpen = true;
    upcomingChampionshipsOpen = false;
    pastChampionshipsOpen = false;
    championshipsPerPage: number = 3;
    currentPage: number = 0;
    totalPages: number = 0;
    pageableCurrentChampionships = { page: 0, size: 3 };
    pageableUpcomingChampionships = { page: 0, size: 3 };
    pageablePastChampionships = { page: 0, size: 3 };
    pageable: Pageable = new Pageable(0, 3);
    selectedChampionshipId: number;

    constructor(
        private championshipService: ChampionshipService,
        public router: Router,
    ) { }

    ngOnInit(): void {
        this.getChampionships(this.pageableCurrentChampionships).subscribe(
            fetchedChampionships => this.currentChampionships = fetchedChampionships
        );
        this.getChampionships(this.pageableUpcomingChampionships).subscribe(
            fetchedChampionships => this.upcomingChampionships = fetchedChampionships
        );
        this.getChampionships(this.pageablePastChampionships).subscribe(
            fetchedChampionships => this.pastChampionships = fetchedChampionships
        );
    }


    getChampionships(pageable: any): Observable<Championship[]> {
        return this.championshipService.getChampionshipList(pageable).pipe(
            map(response => {
                this.totalPages = response.totalPages.valueOf();
                return response.content;
            }),
            catchError(error => {
                console.error('Failed to fetch championships', error);
                return of([]);
            })
        );
    }



    selectChampionship(championshipId) {
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



    createNewChampionship() {
        this.router.navigateByUrl("championship/new");
    }

    goToChampionship(championshipId) {
        this.router.navigateByUrl("championship/" + championshipId)
    }
}
