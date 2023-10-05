import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import { Race } from '../../model/Race';
import { Grid } from '../../model/Grid';
import { GridService } from '../../service/grid.service';
import { Championship } from '../../model/Championship';
import {AppContext} from "../../util/AppContext";
import {Router} from "@angular/router";
import {Car} from "../../model/Car";
import {User} from "../../model/User";
import {any} from "codelyzer/util/function";
import {SanctionService} from "../../service/sanction.service";

@Component({
    selector: 'app-standings',
    templateUrl: './standings.component.html',
    styleUrls: ['./standings.component.css']
})
export class StandingsComponent implements OnInit, OnChanges {
    @Input() selectedRace: Race;
    @Input() selectedChampionship: Championship;
    @Input() fullMode: boolean;

    @Output() isAlreadyInStandingsChange = new EventEmitter<boolean>();
    @Output() selectedGridChange = new EventEmitter<Grid>();

    grid: Array<Grid>;
    filteredGrid: Array<Grid>;
    teamGrid: Array<any>;
    selectedGrid: Grid;
    isAlreadyInStandings: boolean = false;
    selectedValue: string = "OVERALL";

    constructor(
        private gridService: GridService,
        public appContext: AppContext,
        private router: Router
    ) {}

    ngOnInit(): void {
        this.loadGridForChampionship();
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes.selectedChampionship && !changes.selectedChampionship.firstChange) {
            this.loadGridForChampionship();
        }
    }


    loadGridForChampionship(): void {
        if (this.selectedChampionship?.id) {
            this.gridService.getGridForChampionship(this.selectedChampionship.id).subscribe(
                (gridData) => {
                    this.grid = gridData;
                    this.grid.sort((a, b) => b.points - a.points);
                    this.filteredGrid = this.grid;
                    for (const grid of this.grid) {
                        this.selectGrid(grid)
                    }
                },
                (error) => {
                    console.error('Error fetching grid data:', error);
                }
            );
        }
    }


    isGridFromLoggedUser(grid: Grid): boolean {
        if (grid.driversList.find(driver => this.appContext.isLoggedUser(driver))) {
            this.isAlreadyInStandings = true;
        }
        return grid.driversList.find(driver => this.appContext.isLoggedUser(driver)) !== undefined;
    }

    selectGrid(grid: Grid): void {
        if (this.isGridFromLoggedUser(grid)) {
            this.selectedGrid = grid;
            this.selectedGridChange.emit(this.selectedGrid)
        }
    }

    handleRowClick(selectedGridItem: Grid) {
        if (this.appContext.isAdmin() && this.fullMode == true) {
            this.selectedGrid = selectedGridItem;
        } else if (this.isGridFromLoggedUser(selectedGridItem)) {
            this.selectedGrid = selectedGridItem;
        }
        this.selectedGridChange.emit(this.selectedGrid);
    }

    editGrid(grid: Grid) {
        let championship = this.selectedChampionship;
        this.router.navigateByUrl("join", {state: {grid: grid, championship: championship}});
    }

    deleteGrid() {
        this.gridService.deleteGrid(this.selectedGrid.id).subscribe(() => {
            this.router.navigateByUrl("championship")
        })
    }

    onSelectionChange() {
        if (this.selectedValue === "PRO" || this.selectedValue === "SILVER") {
            this.filteredGrid = this.grid.filter(item => item.carLicense === this.selectedValue);
        } else if (this.selectedValue === 'TEAM') {
            let teamNameList = [];
            this.teamGrid = [];
            this.grid.forEach(grid => {
               if(!teamNameList.find(teamName => teamName === grid.teamName)){
                   teamNameList.push(grid.teamName);
               }
            });
            teamNameList.forEach(teamName => {
                let teamGridSplit = this.grid.filter(grid => grid.teamName === teamName);
                let teamPoints = 0;
                let teamCars = [];
                let teamDrivers = [];
                teamGridSplit.forEach(gridForTeam => {
                    teamPoints += gridForTeam.points;
                    if (!teamCars.find(car => car.id === gridForTeam.car.id)) {
                        teamCars.push(gridForTeam.car);
                    }
                    teamDrivers = [].concat(teamDrivers, gridForTeam.driversList);
                });
                let teamGridToAdd = {
                    teamName: teamName,
                    points: teamPoints,
                    carList : teamCars,
                    driversList: teamDrivers
                }
                this.teamGrid.push(teamGridToAdd);
            });
        } else {
            this.filteredGrid = [...this.grid];
        }
    }
}
