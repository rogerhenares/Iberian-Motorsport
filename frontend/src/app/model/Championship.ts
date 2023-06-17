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
    startDate: String;

    constructor() {
        this.id = null;
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
        this.dumpLeaderboards= 0;
        this.isRaceLocked= 0;
        this.randomizeTrackWhenEmpty= null;
        this.centralEntryListPath= '';
        this.allowAutoDQ= null;
        this.shortFormationLap= null;
        this.dumpEntryList= null;
        this.formationLapType= null;
        this.ignorePrematureDisconnects= null;
        this.imageContent= '';
        this.startDate = null;
    }
}