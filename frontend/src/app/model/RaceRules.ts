export class RaceRules  {
    id: Number;
    qualifyStandingType: Number;
    pitWindowLengthSec: Number;
    driverStintTimeSec: Number;
    mandatoryPitstopCount: Number;
    maxTotalDrivingTime: Number;
    maxDriversCount: Number;
    isRefuellingAllowedInRace: Number;
    isRefuellingTimeFixed: Number;
    isMandatoryPitstopRefuellingRequired: Number;
    isMandatoryPitstopTyreChangeRequired: Number;
    isMandatoryPitstopSwapDriverRequired: Number;
    tyreSetCount: Number;

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
    }
}