import {RaceRules} from "./RaceRules";
import {Session} from "./Session";
import {Grid} from "./Grid";

export class Race {
    id: number;
    track: String;
    preRaceWaitingTimeSeconds: Number;
    sessionOverTimeSeconds: Number;
    ambientTemp: Number;
    cloudLevel: Number;
    rain: Number;
    weatherRandomness: Number;
    postQualySeconds: Number;
    postRaceSeconds: Number;
    serverName: String;
    sessionCount: Number;
    startDate: String;
    raceRulesDTO: RaceRules;
    sessionDTOList: Array<Session>;
    grid: Grid;

    constructor() {
        this.id= null;
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
    }
}