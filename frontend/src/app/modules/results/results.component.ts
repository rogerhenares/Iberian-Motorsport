import {Component, Input, OnChanges, OnInit, SimpleChanges} from "@angular/core";
import {Race} from "../../model/Race";
import {Championship} from "../../model/Championship";
import {Grid} from "../../model/Grid";
import {GridService} from "../../service/grid.service";
import {GridRaceService} from "../../service/gridrace.service";
import {GridRace} from "../../model/GridRace";
import {AppContext} from "../../util/AppContext";

@Component({
    selector: 'app-results',
    templateUrl: './results.component.html',
    styleUrls: ['./results.component.css']
})
export class ResultsComponent implements OnInit, OnChanges{

    @Input() selectedRace: Race;
    @Input() selectedChampionship: Championship;
    @Input() fullMode: boolean;
    @Input() maxHeight: number;

    grid: Array<Grid>;
    gridRace: Array<GridRace>;
    gridRaceFasterLap: GridRace;
    gridRaceFasterQualyLap: GridRace;
    gridRaceWinner: GridRace;
    maxTableHeight: number = 60;

    constructor(
        private gridService: GridService,
        private gridRaceService: GridRaceService,
        public appContext: AppContext
    ) {
    }

    ngOnInit(): void {
        this.loadGridForChampionship();
        if(this.maxHeight) {
            this.maxTableHeight = this.maxHeight - 5;
        }
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
                    this.gridRace.sort((a, b) => {if(b.points !== a.points) return b.points - a.points; else return b.finalTime - a.finalTime;});
                    this.gridRace.forEach(gridRace => {
                        gridRace.grid = this.grid.find(grid => grid.id === gridRace.gridId);
                    });
                    this.gridRaceFasterLap = Array.from(this.gridRace)
                        .filter(a => a.firstSector > 0 && a.secondSector > 0 && a.thirdSector > 0)
                        .sort((a,b) =>
                            (b.firstSector + b.secondSector + b.thirdSector) -
                            (a.firstSector + a.secondSector + a.thirdSector)).pop();
                    this.gridRaceFasterQualyLap = Array.from(this.gridRace)
                        .filter(a => a.qualyFirstSector > 0 && a.qualySecondSector > 0 && a.qualyThirdSector > 0)
                        .sort((a,b) =>
                            (b.qualyFirstSector + b.qualySecondSector + b.qualyThirdSector) -
                            (a.qualyFirstSector + a.qualySecondSector + a.qualyThirdSector)).pop();
                    this.gridRaceWinner = this.gridRace[0];
                    this.scrollToSelectedElement();
                },
                (error) => {
                    console.error('Error fetching grid race data:', error);
                }
            )

        }
    }

    selectGridRaceForLoggedUser(): GridRace{
        return this.gridRace.find(gridRace => this.isGridRaceForLoggedUser(gridRace));
    }

    isGridRaceForLoggedUser(gridRace: GridRace): boolean {
        return gridRace.grid?.driversList.find(driver => this.appContext.isLoggedUser(driver)) !== undefined;
    }

    isFasterLapGridRace(gridRace: GridRace): boolean {
        return this.gridRaceFasterLap.gridId === gridRace.gridId;
    }

    isFasterQualyLapGridRace(gridRace: GridRace): boolean {
        return this.gridRaceFasterQualyLap.gridId === gridRace.gridId;
    }
    formatTime(totalMilliseconds: number): string {
        const milliseconds = totalMilliseconds % 1000;
        const totalSeconds = Math.floor(totalMilliseconds / 1000);
        const seconds = totalSeconds % 60;
        const minutes = Math.floor(totalSeconds / 60);

        return `${minutes}:${seconds.toString().padStart(2, '0')}.${milliseconds.toString().padStart(3, '0')}`;
    }

    isOnChampionship() {
        let loggedUser = this.appContext.getLoggedUser()
        return this.grid.find(grid => grid.driversList.find(driver => driver.userId === loggedUser.userId));
    }

    addSanctionToTotalTime(gridRace: GridRace) {
        if(gridRace.gridId !== this.gridRaceWinner.gridId) {
            return gridRace.finalTime + (gridRace.sanctionTime * 1000) - this.gridRaceWinner.finalTime;
        }
        return gridRace.finalTime + (gridRace.sanctionTime * 1000);
    }

    scrollToSelectedElement(): void {
        setTimeout(() => {
            let selectedGrid = this.selectGridRaceForLoggedUser();
            if(selectedGrid){
                var id = "gridRaceTR"+selectedGrid.grid.id;
                var elem = document.getElementById(id);
                if(elem) {
                    elem.scrollIntoView({ behavior: 'smooth', block: 'center' });
                }
            }
        }, 700);
    }
}
