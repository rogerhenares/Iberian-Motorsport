import {Race} from "./Race";
import {Car} from "./Car";
import {ChampionshipCategory} from "./ChampionshipCategory";

export class Championship {
    id: number;
    name: string;
    description: string;
    adminPassword: string;
    carGroup: string;
    trackMedalsRequirement: number;
    safetyRatingRequirement: number;
    racecraftRatingRequirement: number;
    password: string;
    spectatorPassword: string;
    maxCarSlots: number;
    dumpLeaderboards: number;
    isRaceLocked: number;
    randomizeTrackWhenEmpty: number;
    centralEntryListPath: any;
    allowAutoDq: number;
    shortFormationLap: number;
    dumpEntryList: number;
    formationLapType: number;
    ignorePrematureDisconnects: number;
    imageContent: string;
    startDate: string;
    raceList: Array<Race>;
    nextRace: Race;
    disabled: boolean;
    started: boolean;
    finished: boolean;
    championshipCategoryList: Array<ChampionshipCategory>;
    carList: Array<Car>;

    constructor() {
        this.id = -1;
        this.name= '';
        this.description= '';
        this.adminPassword= '';
        this.carGroup= '';
        this.trackMedalsRequirement= null;
        this.safetyRatingRequirement= null;
        this.racecraftRatingRequirement= null;
        this.password= '';
        this.spectatorPassword= '';
        this.maxCarSlots= null;
        this.dumpLeaderboards= 1;
        this.isRaceLocked= 0;
        this.randomizeTrackWhenEmpty= null;
        this.centralEntryListPath= null;
        this.allowAutoDq = 0;
        this.shortFormationLap= 0;
        this.dumpEntryList= 1;
        this.formationLapType= 0;
        this.ignorePrematureDisconnects= 0;
        this.imageContent= null;
        this.startDate = null;
        this.raceList = null;
        this.disabled = true;
        this.started = false;
        this.finished = false;
        this.nextRace = null;
        this.carList = null;
        this.championshipCategoryList = null;
    }
}