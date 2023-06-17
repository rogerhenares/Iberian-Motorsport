export class Championship {
    id: Number;
    name: String;
    description: String;
    adminPassword: String;
    carGroup: String;
    trackMedalsRequirement: Number;
    safetyRatingRequirement: Number;
    racecraftRatingRequirement: Number;
    password: String;
    spectatorPassword: String;
    maxCarSlots: Number;
    dumpLeaderboards: Number;
    isRaceLocked: Number;
    randomizeTrackWhenEmpty: Number;
    centralEntryListPath: String;
    allowAutoDQ: Number;
    shortFormationLap: Number;
    dumpEntryList: Number;
    formationLapType: Number;
    ignorePrematureDisconnects: Number;
    imageContent: String;
    startDate: Date;

    constructor() {
        this.id = null;
        this.name= '';
        this.description= '';
        this.adminPassword= '';
        this.carGroup= '';
        this.trackMedalsRequirement= 0;
        this.safetyRatingRequirement= 0;
        this.racecraftRatingRequirement= 0;
        this.password= '';
        this.spectatorPassword= '';
        this.maxCarSlots= 30;
        this.dumpLeaderboards= 0;
        this.isRaceLocked= 0;
        this.randomizeTrackWhenEmpty= 0;
        this.centralEntryListPath= '';
        this.allowAutoDQ= 0;
        this.shortFormationLap= 0;
        this.dumpEntryList= 0;
        this.formationLapType= 0;
        this.ignorePrematureDisconnects= 0;
        this.imageContent= '';
        this.startDate = new Date;
    }
}