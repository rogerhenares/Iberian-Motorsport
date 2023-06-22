import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import {ChampionshipService} from "../../service/championship.service";
import {Championship} from "../../model/Championship";
import {Race} from "../../model/Race";
import {Pageable} from "../../model/Pageable";

@Component({
    selector: 'app-championship-details',
    templateUrl: './championship-details.component.html',
    styleUrls: ['./championship-details.component.css']
})

export class ChampionshipDetailsComponent implements OnInit {
    races: Race[]
    championshipId: number;
    championship: Championship;
    selectedRaceId: number | null = null;
    totalPages: number = 0;
    pageable: Pageable = new Pageable(0, 3);

    constructor(
        private route: ActivatedRoute,
        private http: HttpClient,
        private championshipService: ChampionshipService
    ) {
    }

    ngOnInit() {
        this.route.paramMap.subscribe(params => {
            const championshipId = +params.get('championshipId');
            this.fetchChampionshipDetails(championshipId);
            console.log(this.championship)
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
                    console.log(this.championship)
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