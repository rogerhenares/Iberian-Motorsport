import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ChampionshipService} from "../../service/championship.service";
import {Championship} from "../../model/Championship";
import {Race} from "../../model/Race";
import {Pageable} from "../../model/Pageable";
import {AppContext} from "../../util/AppContext";
import {RaceService} from "../../service/race.service";
import {SwalComponent} from "@sweetalert2/ngx-sweetalert2";
import Swal from "sweetalert2";
import {StandingsComponent} from "../standings/standings.component";
import {Grid} from "../../model/Grid";
import {GridService} from "../../service/grid.service";

@Component({
    selector: 'app-championship-details',
    templateUrl: './championship-details.component.html',
    styleUrls: ['./championship-details.component.css']
})

export class ChampionshipDetailsComponent implements OnInit {

    @ViewChild('requestFailSwal', {static : true}) requestFailSwal: SwalComponent;
    @ViewChild('requestSuccessSwal', {static : true}) requestSuccessSwal: SwalComponent;

    @ViewChild(StandingsComponent, {static: true}) standings: StandingsComponent;

    races: Race[];
    selectedRace: Race;
    championshipId: number;
    championship: Championship;
    selectedRaceId: number | null = null;
    totalPages: number;
    pageable: Pageable = new Pageable(0, 3);

    selectedGrid: Grid;
    isAlreadyInStandings: boolean;

    constructor(
        private route: ActivatedRoute,
        private championshipService: ChampionshipService,
        public appContext : AppContext,
        public router: Router,
        public raceService: RaceService,
        public gridService: GridService
    ) {
    }

    ngOnInit() {
        this.route.paramMap.subscribe(params => {
            const championshipId = +params.get('championshipId');
            this.fetchChampionshipDetails(championshipId);
            console.log("Championship ->" ,this.championship)
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
        this.router.navigateByUrl("/race/new", {state: {championshipId: championshipId}});
    }

    editRace(championshipId: number, race: Race) {
        this.router.navigateByUrl("/race/new", { state: { championshipId: championshipId, race: race } });
    }

    deleteRace() {
        this.raceService.deleteRace(this.selectedRaceId).subscribe(() => {
            window.location.reload();
        })
    }


    joinChampionship() {
        let championship: Championship = this.championship
        this.router.navigate(['/join'], { state: { championship: championship} });
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
        console.log("Selected Grid", this.selectedGrid);
    }

    editGrid(grid: Grid): void {
        let championship = this.championship;
        this.router.navigateByUrl("join", {state: {grid: grid, championship: championship}});
    }

    leaveChampionship() {
        if (this.championship.style == 'TEAM') {
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
        let championship = this.championship
        this.gridService.getGridByPassword(password).subscribe((grid: Grid) => {
            this.router.navigateByUrl("join", {state: {grid: grid, championship: championship}});

        })
    }

    isGridManager(grid: Grid) {
        return grid.managerId === this.appContext.getLoggedUser().userId;
    }

}

