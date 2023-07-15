import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import {ChampionshipService} from "../../service/championship.service";
import {Championship} from "../../model/Championship";
import {Race} from "../../model/Race";
import {Pageable} from "../../model/Pageable";
import {TimezoneService} from "../../service/timezone.service";

@Component({
    selector: 'app-championship-details',
    templateUrl: './championship-details.component.html',
    styleUrls: ['./championship-details.component.css']
})

export class ChampionshipDetailsComponent implements OnInit {
    races: Race[];
    upcomingRaces: Race[];
    championshipId: number;
    championship: Championship;
    selectedRaceId: number | null = null;
    totalPages: number = 0;
    pageable: Pageable = new Pageable(0, 3);

    constructor(
        private route: ActivatedRoute,
        private http: HttpClient,
        private championshipService: ChampionshipService,
        private timezoneService: TimezoneService
    ) {
    }

    ngOnInit() {
        this.route.paramMap.subscribe(params => {
            const championshipId = +params.get('championshipId');
            this.fetchChampionshipDetails(championshipId);
            console.log(this.championship)
        });

        this.championship.raceList.forEach(race => {
            if (new Date(race.startDate.toString()) > new Date()) {
                race.startDate = this.timezoneService.convertDateToUserTimezone(race.startDate.toString());
                this.upcomingRaces.push(race);
            }
        });
    }


    createNewRace() {

    }

    fetchChampionshipDetails(championshipId: number) {
        this.championshipService.getChampionshipById(championshipId)
            .subscribe(
                (response: any) => {
                    console.log('API response:', response);
                    this.championship = response;
                },
                (error: any) => {
                    console.error('Error fetching championship details:', error);
                }
            );
    }

    selectRace(raceId) {
        this.selectedRaceId = raceId;
    }

}