import {Component, NgModule, OnInit} from '@angular/core';
import {ChampionshipService} from "../../service/championship.service";
import {Championship} from "../../model/Championship";
import {Pageable} from "../../model/Pageable";
import {ActivatedRoute, Router} from '@angular/router';
import {Observable} from "rxjs";
import {DatePipe} from "@angular/common";
import {TimezoneService} from "../../service/timezone.service";
import {Race} from "../../model/Race";



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
    selectedChampionship: Championship;
    upcomingRaces: Race[];
    closestRace: Race;


    constructor(
        private championshipService: ChampionshipService,
        public router: Router,
        private datePipe: DatePipe,
        private timezoneService: TimezoneService
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

                this.championships.forEach(championship => {
                    this.closestRace = this.getClosestRace(championship);
                });
            },
            (error: any) => {
                console.error('Failed to fetch championships', error);
            }
        );
    }


    selectChampionship(championshipId) {
        this.selectedChampionshipId = championshipId;
        this.championshipService.getChampionshipById(this.selectedChampionshipId).subscribe(championship => {
            this.selectedChampionship = championship as Championship;

            this.selectedChampionship.raceList = this.getUpcomingRaces(this.selectedChampionship)
                .sort((a, b) => new Date(a.startDate.toString()).valueOf() - new Date(b.startDate.toString()).valueOf());


            console.log(this.selectedChampionship);
        });
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

    transformDate(date: string): string {
        return this.datePipe.transform(new Date(date), 'short', this.timezoneService.userTimezone);
    }

    formatDate(date: string): string {
        return this.transformDate(date);
    }

    getHour(date: string): string {
        return this.datePipe.transform(this.transformDate(date), 'HH');
    }

    getUpcomingRaces(championship: any): any[] {
        const nowInUserTimezone = new Date().toLocaleString("en-US", {timeZone: this.timezoneService.userTimezone});
        return championship.raceList.filter(race => new Date(race.startDate.toString()) > new Date(nowInUserTimezone));
    }

    getClosestRace(championship: any): any {
        const nowInUserTimezone = new Date().toLocaleString("en-US", {timeZone: this.timezoneService.userTimezone});
        const upcomingRaces = championship.raceList.filter(race => new Date(race.startDate.toString()).valueOf() > new Date(nowInUserTimezone).valueOf());
        return upcomingRaces.sort((a, b) => new Date(a.startDate).valueOf() - new Date(b.startDate).valueOf())[0];
    }

}

