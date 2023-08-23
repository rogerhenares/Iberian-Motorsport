import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { HttpClient } from '@angular/common/http';
import {ChampionshipService} from "../../service/championship.service";
import {Championship} from "../../model/Championship";
import {Race} from "../../model/Race";
import {Pageable} from "../../model/Pageable";
import {TimezoneService} from "../../service/timezone.service";
import {AppContext} from "../../util/AppContext";
import {RaceService} from "../../service/race.service";
import {GridService} from "../../service/grid.service";
import {Grid} from "../../model/Grid";

@Component({
    selector: 'app-championship-details',
    templateUrl: './championship-details.component.html',
    styleUrls: ['./championship-details.component.css']
})

export class ChampionshipDetailsComponent implements OnInit {

    races: Race[];
    selectedRace: Race;
    upcomingRaces: Race[];
    championshipId: number;
    championship: Championship;
    selectedRaceId: number | null = null;
    totalPages: number;
    pageable: Pageable = new Pageable(0, 3);

    constructor(
        private route: ActivatedRoute,
        private http: HttpClient,
        private championshipService: ChampionshipService,
        private timezoneService: TimezoneService,
        public appContext : AppContext,
        public router: Router,
        public raceService: RaceService,
        private gridService: GridService,
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


    fetchChampionshipDetails(championshipId: number) {
        this.championshipService.getChampionshipById(championshipId)
            .subscribe(
                (response: any) => {
                    console.log('API response:', response);
                    this.championship = response;
                    this.races = this.championship.raceList;
                    // Calculate total pages after getting races
                    this.totalPages = Math.ceil(this.races.length / this.pageable.size);

                    // Select the first race by default
                    if (this.races && this.races.length > 0) {
                        this.selectedRaceId = this.races[0].id;
                        this.selectedRace = this.races[0]
                    }
                },
                (error: any) => {
                    console.error('Error fetching championship details:', error);
                }
            );
    }


    selectRace(raceId) {
        this.selectedRaceId = raceId;
        this.selectedRace = this.championship.raceList.find(race => race.id === raceId);
    }

    pagedData(): Race[] {
        const start = this.pageable.page * this.pageable.size;
        const end = start + this.pageable.size;
        return this.races.slice(start, end);
    }

    nextPage() {
        this.pageable.page++;
    }

    previousPage() {
        if (this.pageable.page > 0) {
            this.pageable.page--;
        }
    }

    getImage(trackName: string) {
        const trimmedTrackName = trackName.trim();
        const sanitizedTrackName = trimmedTrackName.replace(/\s+/g, "");
        return "assets/img/" + sanitizedTrackName + ".png";
    }

    createNewRace(championshipId: number) {
        console.log(this.championship)
        console.log(championshipId)
        this.router.navigateByUrl("/race/new", {state: {championshipId: championshipId}});
    }

    editRace(championshipId: number, race: Race) {
        this.router.navigateByUrl("/race/new", { state: { championshipId: championshipId, race: race } });
    }

    deleteRace() {
        console.log("delete race click")
        this.raceService.deleteRace(this.selectedRaceId).subscribe()
    }

    joinChampionship() {
        let championship: Championship = this.championship
        console.log("Championship", championship)
        this.router.navigate(['/join'], { state: { championship: championship} });
    }

}