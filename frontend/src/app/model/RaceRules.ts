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
        this.qualifyStandingType= null;
        this.pitWindowLengthSec= null;
        this.driverStintTimeSec= null;
        this.mandatoryPitstopCount= null;
        this.maxTotalDrivingTime= null;
        this.maxDriversCount= null;
        this.isRefuellingAllowedInRace= null;
        this.isRefuellingTimeFixed= null;
        this.isMandatoryPitstopRefuellingRequired= null;
        this.isMandatoryPitstopTyreChangeRequired= null;
        this.isMandatoryPitstopSwapDriverRequired= null;
        this.tyreSetCount= null;
        this.raceId = null;
    }
}