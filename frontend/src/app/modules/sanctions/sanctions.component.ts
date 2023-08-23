import {Component, Input, OnChanges, OnInit, SimpleChanges} from "@angular/core";
import {Grid} from "../../model/Grid";
import {Race} from "../../model/Race";
import {GridRace} from "../../model/GridRace";
import {GridRaceService} from "../../service/gridrace.service";
import {AppContext} from "../../util/AppContext";
import {GridService} from "../../service/grid.service";
import {Championship} from "../../model/Championship";

@Component({
    selector: 'app-sanctions',
    templateUrl: './sanctions.component.html',
    styleUrls: ['./sanctions.component.css']
})
export class SanctionsComponent implements OnInit, OnChanges {

    @Input() selectedRace: Race;
    @Input() selectedChampionship: Championship;

    grid: Array<Grid>;
    gridRace: Array<GridRace>;

    constructor(
        private gridRaceService: GridRaceService,
        private appContext: AppContext,
        private gridService: GridService
    ) {}

    ngOnInit(): void {
        this.loadGridForChampionship()
    }

    ngOnChanges(changes: SimpleChanges) {
        if (changes.selectedRace && !changes.selectedRace.firstChange) {
            this.loadGridRaceForRace();
            console.log("GridRace:", this.gridRace)
        }
    }

    loadGridForChampionship(): void {
        if (this.selectedChampionship.id) {
            this.gridService.getGridForChampionship(this.selectedChampionship.id).subscribe(
                (gridData) => {
                    this.grid = gridData;
                    this.loadGridRaceForRace();
                },
                (error) => {
                    console.error('Error fetching grid data:', error);
                }
            );
        }
    }


    loadGridRaceForRace(): void {
        if (this.selectedRace.id) {
            this.gridRaceService.getGridRaceForRace(this.selectedRace.id).subscribe(
                (gridRace) => {
                    this.gridRace = gridRace;
                    this.gridRace.forEach(gridRace => {
                        gridRace.grid = this.grid.find(grid => grid.id === gridRace.gridId);
                    });
                    },
                (error) => {
                    console.error('Error fetching grid race data:', error);
                }
            )

        }
    }

    isGridRaceForLoggedUser(gridRace: GridRace): boolean {
        return gridRace.grid.driversList.find(driver => this.appContext.isLoggedUser(driver)) !== undefined;
    }

}