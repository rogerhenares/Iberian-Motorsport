import {Race} from "./Race";

export class RaceRules  {
    id: number;
    qualifyStandingType: number;
    pitWindowLengthSec: number;
    driverStintTimeSec: number;
    mandatoryPitstopCount: number;
    maxTotalDrivingTime: number;
    maxDriversCount: number;
    isRefuellingAllowedInRace: number;
    isRefuellingTimeFixed: number;
    isMandatoryPitstopRefuellingRequired: number;
    isMandatoryPitstopTyreChangeRequired: number;
    isMandatoryPitstopSwapDriverRequired: number;
    tyreSetCount: number;
    raceId: number;

    constructor() {
        this.id= null;
        this.qualifyStandingType= 1;
        this.pitWindowLengthSec= 3660;
        this.driverStintTimeSec= -1;
        this.mandatoryPitstopCount= 1;
        this.maxTotalDrivingTime= 0;
        this.maxDriversCount= 1;
        this.isRefuellingAllowedInRace= 1;
        this.isRefuellingTimeFixed= 0;
        this.isMandatoryPitstopRefuellingRequired= 1;
        this.isMandatoryPitstopTyreChangeRequired= 0;
        this.isMandatoryPitstopSwapDriverRequired= 0;
        this.tyreSetCount= 50;
        this.raceId = null;
    }

    static defaultRaceRuleWithValues(): RaceRules {
        //TODO in the future :D
        let raceRule = new RaceRules();
        return raceRule;
    }
}