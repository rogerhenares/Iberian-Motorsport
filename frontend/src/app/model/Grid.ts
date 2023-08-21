import {User} from "./User";
import {Car} from "./Car";


export class Grid {
    id: number;
    carNumber: number;
    carLicense: string;
    carId: number;
    championshipId: number;
    driversList: Array<User>;
    car: Car;
    points: number;

    constructor() {
        this.id = null;
        this.carNumber = null;
        this.carLicense = "";
        this.carId = null;
        this.championshipId = null;
        this.driversList = [];
        this.car = null;
        this.points = null;
    }
}