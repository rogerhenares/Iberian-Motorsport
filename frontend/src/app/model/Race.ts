import {RaceRules} from "./RaceRules";
import {Session} from "./Session";
import {Grid} from "./Grid";
import {Bop} from "./Bop";

export class Race {
    id: number;
    track: string;
    preRaceWaitingTimeSeconds: number;
    sessionOverTimeSeconds: number;
    ambientTemp: number;
    cloudLevel: number;
    rain: number;
    weatherRandomness: number;
    postQualySeconds: number;
    postRaceSeconds: number;
    serverName: string;
    sessionCount: number;
    startDate: string;
    raceRulesDTO: RaceRules;
    sessionDTOList: Session[];
    bopDTOList: Bop[];
    grid: Grid;
    championshipId: number;
    status: string;

    constructor() {
        this.id= -1;
        this.track= '';
        this.preRaceWaitingTimeSeconds= 150;
        this.sessionOverTimeSeconds= 150;
        this.ambientTemp= 19;
        this.cloudLevel= 0.35;
        this.rain=0.1;
        this.weatherRandomness= 2;
        this.postQualySeconds= 150;
        this.postRaceSeconds= 180;
        this.serverName= '';
        this.sessionCount= null;
        this.startDate = null;
        this.raceRulesDTO = null;
        this.sessionDTOList = [];
        this.bopDTOList = [];
        this.grid = null;
        this.championshipId = null;
        this.status = "PENDING";
    }
}