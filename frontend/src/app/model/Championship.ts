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
    style: string;
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
        this.description= 'Campeonato de IML';
        this.adminPassword= '';
        this.carGroup= 'GT3';
        this.trackMedalsRequirement= 3;
        this.safetyRatingRequirement= 70;
        this.racecraftRatingRequirement= -1;
        this.password= 'IML2023';
        this.spectatorPassword= 'IML2023';
        this.maxCarSlots= 103;
        this.dumpLeaderboards= 1;
        this.isRaceLocked= 1;
        this.randomizeTrackWhenEmpty= null;
        this.centralEntryListPath= null;
        this.allowAutoDq = 0;
        this.shortFormationLap= 0;
        this.dumpEntryList= 1;
        this.formationLapType= 0;
        this.ignorePrematureDisconnects= 0;
        this.imageContent= null;
        this.startDate = null;
        this.style = null;
        this.raceList = null;
        this.disabled = true;
        this.started = false;
        this.finished = false;
        this.nextRace = null;
        this.carList = null;
        this.championshipCategoryList = null;
    }
}