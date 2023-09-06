import {Grid} from "./Grid";


export class Sanction {
    id: number;
    lap: number;
    penalty: number;
    reason: string;
    raceId: number;
    gridId: number;
    grid: Grid;
    inGame: boolean;
    licensePoints: number;

    constructor() {
        this.id = null;
        this.lap = null;
        this.penalty = null;
        this.reason = "";
        this.gridId = null;
        this.raceId = null;
        this.grid = null;
        this.inGame = null;
        this.licensePoints = null;
    }
}
