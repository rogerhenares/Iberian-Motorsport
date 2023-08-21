import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import { Race } from '../../model/Race';
import { Grid } from '../../model/Grid';
import { GridService } from '../../service/grid.service';
import { Championship } from '../../model/Championship';
import {AppContext} from "../../util/AppContext";

@Component({
    selector: 'app-standings',
    templateUrl: './standings.component.html',
    styleUrls: ['./standings.component.css']
})
export class StandingsComponent implements OnInit, OnChanges {
    @Input() selectedRace: Race;
    @Input() selectedChampionship: Championship;
    grid: Array<Grid>;

    constructor(
        private gridService: GridService,
        public appContext: AppContext
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
        if (this.selectedChampionship.id) {
            this.gridService.getGridForChampionship(this.selectedChampionship.id).subscribe(
                (gridData) => {
                    this.grid = gridData;
                    this.grid.sort((a, b) => b.points - a.points);
                    console.log(this.grid);
                },
                (error) => {
                    console.error('Error fetching grid data:', error);
                }
            );
        }
    }

    isGridFromLoggedUser(grid: Grid): boolean {
        return grid.driversList.find(driver => this.appContext.isLoggedUser(driver)) !== undefined;
    }

}
