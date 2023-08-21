import {Component, Input, OnChanges, OnInit, SimpleChanges} from "@angular/core";
import {Race} from "../../model/Race";
import {Championship} from "../../model/Championship";
import {Grid} from "../../model/Grid";
import {GridService} from "../../service/grid.service";
import {GridRaceService} from "../../service/gridrace.service";
import {GridRace} from "../../model/GridRace";
import {AppContext} from "../../util/AppContext";
import {sum} from "chartist";

@Component({
    selector: 'app-results',
    templateUrl: './results.component.html',
    styleUrls: ['./results.component.css']
})
export class ResultsComponent implements OnInit, OnChanges{

    @Input() selectedRace: Race;
    @Input() selectedChampionship: Championship;
    @Input() fullMode: boolean;

    grid: Array<Grid>;
    gridRace: Array<GridRace>;
    gridRaceFasterLap: GridRace;

    constructor(
        private gridService: GridService,
        private gridRaceService: GridRaceService,
        private appContext: AppContext
    ) {
    }

    ngOnInit(): void {
        this.loadGridForChampionship();
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes.selectedRace && !changes.selectedRace.firstChange) {
            this.loadGridRaceForRace();
        }
    }

    loadGridForChampionship(): void {
        if (this.selectedChampionship.id) {
            this.gridService.getGridForChampionship(this.selectedChampionship.id).subscribe(
                (gridData) => {
                    this.grid = gridData;
                    this.grid.sort((a, b) => b.points - a.points);
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
                    this.gridRace.sort((a, b) => b.points - a.points);
                    this.gridRace.forEach(gridRace => {
                        gridRace.grid = this.grid.find(grid => grid.id === gridRace.gridId);
                    });
                    this.gridRaceFasterLap = Array.from(this.gridRace).sort((a,b) =>
                        (b.firstSector + b.secondSector + b.thirdSector) -
                        (a.firstSector + a.secondSector + a.thirdSector)).pop();
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

    isFasterLapGridRace(gridRace: GridRace): boolean {
        return this.gridRaceFasterLap.gridId === gridRace.gridId;
    }

    formatTime(totalMilliseconds: number): string {
        const milliseconds = totalMilliseconds % 1000;
        const totalSeconds = Math.floor(totalMilliseconds / 1000);
        const seconds = totalSeconds % 60;
        const minutes = Math.floor(totalSeconds / 60);

        return `${minutes}:${seconds.toString().padStart(2, '0')}.${milliseconds.toString().padStart(3, '0')}`;
    }

}