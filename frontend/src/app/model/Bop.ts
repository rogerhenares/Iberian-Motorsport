import {Car} from "./Car";

export class Bop {
    raceId: number;
    car: Car;
    ballastKg: number;
    restrictor: number;

    constructor() {
        this.raceId = null;
        this.car = null;
        this.ballastKg = 0;
        this.restrictor = 0;
    }
}