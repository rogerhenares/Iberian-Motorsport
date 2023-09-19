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
        this.id = this.id || null;
        this.carNumber = this.carNumber || 0;
        this.carLicense = this.carLicense || "";
        this.teamName = this.teamName || "";
        this.championshipId = this.championshipId || null;
        this.driversList = this.driversList || [];
        this.car = this.car || null;
        this.points = this.points || 0;
        this.licensePoints = this.licensePoints || 0;
        this.disabled = this.disabled || null;
    }
}