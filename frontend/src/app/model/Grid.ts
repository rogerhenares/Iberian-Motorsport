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
    pointsDrop: number;
    licensePoints: number;
    password: string;
    managerId: number;
    disabled: boolean;
    newManagerId: number;

    constructor() {
        this.id = this.id || -1;
        this.carNumber = this.carNumber || 0;
        this.carLicense = "PRO";
        this.teamName = this.teamName || "";
        this.championshipId = this.championshipId || null;
        this.driversList = this.driversList || [];
        this.car = this.car || null;
        this.points = this.points || 0;
        this.pointsDrop = this.points || 0;
        this.licensePoints = this.licensePoints || 0;
        this.password = this.password || "";
        this.managerId = this.managerId || null;
        this.disabled = this.disabled || null;
        this.newManagerId = null;
    }
}
