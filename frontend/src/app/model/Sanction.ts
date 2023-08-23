import {Grid} from "./Grid";


export class Sanction {
    id: number;
    time: number;
    lap: number;
    sector: number;
    penalty: number;
    description: string;
    gridId: number;
    raceId: number;
    grid: Grid;

    constructor() {
        this.id = null;
        this.time = null;
        this.lap = null;
        this.sector = null;
        this.penalty = null;
        this.description = "";
        this.gridId = null;
        this.raceId = null;
        this.grid = null;
    }
}