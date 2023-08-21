import {RaceRules} from "./RaceRules";
import {Session} from "./Session";
import {Grid} from "./Grid";

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
    sessionDTOList: Array<Session>;
    grid: Grid;
    championshipId: number;

    constructor() {
        this.id= -1;
        this.track= '';
        this.preRaceWaitingTimeSeconds= null;
        this.sessionOverTimeSeconds= null;
        this.ambientTemp= null;
        this.cloudLevel= null;
        this.rain= null;
        this.weatherRandomness= null;
        this.postQualySeconds= null;
        this.postRaceSeconds= null;
        this.serverName= '';
        this.sessionCount= null;
        this.startDate = null;
        this.raceRulesDTO = null;
        this.sessionDTOList = null;
        this.grid = null;
        this.championshipId = null;
    }
}