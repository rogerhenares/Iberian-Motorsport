import {User} from "./User";
import {Car} from "./Car";


export class Grid {
    id: number;
    carNumber: number;
    teamName: string;
    carLicense: string;
    championshipId: number;
    driversList: Array<User>;
    car: Car;
    points: number;
    licensePoints: number;
    disabled: boolean

    constructor() {
        this.id = null;
        this.carNumber = null;
        this.carLicense = "";
        this.teamName = "";
        this.championshipId = null;
        this.driversList = [];
        this.car = null;
        this.points = null;
        this.licensePoints = null;
        this.disabled = null;
    }
}