import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import { Race } from '../../model/Race';
import { Grid } from '../../model/Grid';
import { GridService } from '../../service/grid.service';
import { Championship } from '../../model/Championship';
import {AppContext} from "../../util/AppContext";
import {Router} from "@angular/router";

@Component({
    selector: 'app-standings',
    templateUrl: './standings.component.html',
    styleUrls: ['./standings.component.css']
})
export class StandingsComponent implements OnInit, OnChanges {
    @Input() selectedRace: Race;
    @Input() selectedChampionship: Championship;

    @Output() isAlreadyInStandingsChange = new EventEmitter<boolean>();
    @Output() selectedGridChange = new EventEmitter<Grid>();

    grid: Array<Grid>;
    selectedGrid: Grid;
    isAlreadyInStandings: boolean = false;

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
        this.isAlreadyInStandings = true;
        return grid.driversList.find(driver => this.appContext.isLoggedUser(driver)) !== undefined;
    }

    selectGrid(grid: Grid): void {
        if (this.isGridFromLoggedUser(grid)) {
            this.selectedGrid = grid;
            this.selectedGridChange.emit(this.selectedGrid)
        }
    }

    handleRowClick(selectedGridItem: Grid) {
        if (this.appContext.isAdmin()) {
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

}
