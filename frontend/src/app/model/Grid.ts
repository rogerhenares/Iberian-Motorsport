import {User} from "./User";


export class Grid {
    id: number;
    carNumber: number;
    carLicense: string;
    carId: number;
    championshipId: number;
    driversList: Array<number>;

    constructor() {
        this.id = null;
        this.carNumber = null;
        this.carLicense = "";
        this.carId = null;
        this.championshipId = null;
        this.driversList = [];
    }
}