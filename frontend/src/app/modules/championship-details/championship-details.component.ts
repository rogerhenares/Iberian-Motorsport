import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ChampionshipService} from "../../service/championship.service";
import {Championship} from "../../model/Championship";
import {Race} from "../../model/Race";
import {Pageable} from "../../model/Pageable";
import {AppContext} from "../../util/AppContext";
import {RaceService} from "../../service/race.service";
import {SwalComponent} from "@sweetalert2/ngx-sweetalert2";
import {StandingsComponent} from "../standings/standings.component";
import {Grid} from "../../model/Grid";
import {GridService} from "../../service/grid.service";
import {Clipboard} from "@angular/cdk/clipboard";

@Component({
    selector: 'app-championship-details',
    templateUrl: './championship-details.component.html',
    styleUrls: ['./championship-details.component.css']
})

export class ChampionshipDetailsComponent implements OnInit {

    @ViewChild('requestFailSwal', {static : true}) requestFailSwal: SwalComponent;
    @ViewChild('requestSuccessSwal', {static : true}) requestSuccessSwal: SwalComponent;
    @ViewChild('profileNotCompletedSwal', {static: true}) profileNotCompletedSwal: SwalComponent;

    @ViewChild(StandingsComponent, {static: true}) standings: StandingsComponent;

    races: Race[];
    selectedRace: Race;
    championshipId: number;
    championship: Championship;
    selectedRaceId: number | null = null;
    totalPages: number;
    pageable: Pageable = new Pageable(0, 10);

    selectedGrid: Grid;
    isAlreadyInStandings: boolean;

    constructor(
        private route: ActivatedRoute,
        private championshipService: ChampionshipService,
        public appContext : AppContext,
        public router: Router,
        public raceService: RaceService,
        public gridService: GridService,
        public clipboard: Clipboard
    ) {
    }

    ngOnInit() {
        this.route.paramMap.subscribe(params => {
            const championshipId = +params.get('championshipId');
            this.fetchChampionshipDetails(championshipId);
        });
    }

    fetchChampionshipDetails(championshipId: number) {
        this.championshipService.getChampionshipById(championshipId)
            .subscribe(
                (response: any) => {
                    this.championship = response;
                    this.races = this.championship.raceList;
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
        return this.races?.slice(start, end);
    }

    getImage(race: Race) {
        const trimmedTrackName = race.track.trim().toLowerCase();
        const sanitizedTrackName = trimmedTrackName.replace(/\s+/g, "");
        if(race.sessionDTOList.find(session => session.sessionType === "R")?.hourOfDay > 20) {
            return "assets/circuit/" + sanitizedTrackName + "_night.png";
        }
        if(race.rain > 0.3) {
            return "assets/circuit/" + sanitizedTrackName + "_wet.png";
        }
        if(race.cloudLevel > 0.5) {
            return "assets/circuit/" + sanitizedTrackName + "_cloud.png";
        }
        const defaultConfig =  sanitizedTrackName + "_sunny";
        return "assets/circuit/" + defaultConfig + ".png";
    }

    createNewRace(championship: Championship) {
        this.router.navigateByUrl("/race/new", {state: {championshipId: championship?.id, championshipCarList: championship?.carList}});
    }

    editRace(championship: Championship, race: Race) {
        this.router.navigateByUrl("/race/new", { state: { championshipId: championship?.id, race: race, championshipCarList: championship?.carList } });
    }

    deleteRace() {
        this.raceService.deleteRace(this.selectedRaceId).subscribe(() => {
            window.location.reload();
        })
    }


    joinChampionship() {
        if(this.appContext.isLoggedUserActive()) {
            let championship: Championship = this.championship
            this.router.navigate(['/join'], { state: { championship: championship} });
        }
        else {
            this.profileNotCompletedSwal.fire();
        }
    }

    editChampionship(championship: Championship) {
        this.router.navigateByUrl("championship/new", {state: {championship: championship}});
    }


    deleteChampionship() {
        this.championshipService.deleteChampionship(this.championship.id).subscribe(() => {
            this.router.navigateByUrl("championship");
    })
    }

    onIsAlreadyInStandingsChange(value: boolean) {
        this.isAlreadyInStandings = value;
    }

    onSelectedGridChange(grid: Grid) {
        this.selectedGrid = grid;
    }

    editGrid(grid: Grid): void {
        let championship = this.championship;
        this.router.navigateByUrl("join", {state: {grid: grid, championship: championship}});
    }

    leaveChampionship() {
        if (this.championship.style === 'TEAM') {
            this.gridService.removeDriver(this.selectedGrid, this.appContext.getLoggedUser().steamId).subscribe(() => {
                this.router.navigateByUrl("championship")
            })
        }
        else {
            this.gridService.deleteGrid(this.selectedGrid.id).subscribe(() => {
                this.router.navigateByUrl("championship")
                })
            }
        }

    joinTeam(password: string) {
        if (this.appContext.isLoggedUserActive()) {
        let teamSoloJoin: boolean = this.championship.style === 'TEAM-SOLO'
        this.gridService.getGridByPassword(password).subscribe((grid: Grid) => {
            this.router.navigateByUrl("join", {state: {grid: grid, championship: this.championship, teamSoloJoin: teamSoloJoin, password: password}});
        })
        }
        else {
            this.profileNotCompletedSwal.fire();
        }
    }

    isGridManager(grid: Grid) {
        return grid.managerId === this.appContext.getLoggedUser().userId;
    }

    isInChampionship() {
        let loggedUser = this.appContext.getLoggedUser();
        return this.selectedGrid?.driversList.find(driver => driver.userId === loggedUser.userId);
    }

    formattedRace(trackName: string) {
        return trackName.replace(/_/g, ' ');
    }

    isGridDisbled() {
        if (this.selectedGrid != undefined) {
            return this.selectedGrid.disabled;
        }
        return false;
    }
    copyToClipboard(grid: Grid) {
        this.clipboard.copy(grid.password);
    }

}

