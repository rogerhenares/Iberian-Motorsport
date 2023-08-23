import {Sanction} from "./Sanction";
import {Grid} from "./Grid";


export class GridRace {
    points: number;
    firstSector: number;
    secondSector: number;
    thirdSector: number;
    finalTime: number;
    totalLaps: number;
    sanctionDTOList: Array<Sanction>;
    raceId: number;
    gridId: number;
    grid: Grid;

    constructor() {
        this.points = null;
        this.firstSector = null;
        this.secondSector = null;
        this.thirdSector = null;
        this.finalTime = null;
        this.totalLaps = null;
        this.sanctionDTOList= [];
        this.raceId = null;
        this.gridId = null;
        this.grid = null;
    }
}