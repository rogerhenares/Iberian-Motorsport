

export class Sanction {
    id: number;
    time: number;
    lap: number;
    sector: number;
    penalty: number;
    description: string;
    gridId: number;
    raceId: number;

    constructor() {
        this.id = null;
        this.time = null;
        this.lap = null;
        this.sector = null;
        this.penalty = null;
        this.description = "";
        this.gridId = null;
        this.raceId = null;
    }
}