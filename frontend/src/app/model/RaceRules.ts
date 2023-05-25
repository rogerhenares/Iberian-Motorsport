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
        this.id= -1;
        this.qualifyStandingType= -1;
        this.pitWindowLengthSec= -1;
        this.driverStintTimeSec= -1;
        this.mandatoryPitstopCount= -1;
        this.maxTotalDrivingTime= -1;
        this.maxDriversCount= -1;
        this.isRefuellingAllowedInRace= -1;
        this.isRefuellingTimeFixed= -1;
        this.isMandatoryPitstopRefuellingRequired= -1;
        this.isMandatoryPitstopTyreChangeRequired= -1;
        this.isMandatoryPitstopSwapDriverRequired= -1;
        this.tyreSetCount= -1;
    }
}