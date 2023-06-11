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

    constructor() {
        this.id = -1;
        this.name= '';
        this.description= '';
        this.adminPassword= '';
        this.carGroup= '';
        this.trackMedalsRequirement= -1;
        this.safetyRatingRequirement= -1;
        this.racecraftRatingRequirement= -1;
        this.password= '';
        this.spectatorPassword= '';
        this.maxCarSlots= -1;
        this.dumpLeaderboards= -1;
        this.isRaceLocked= -1;
        this.randomizeTrackWhenEmpty= -1;
        this.centralEntryListPath= '';
        this.allowAutoDQ= -1;
        this.shortFormationLap= -1;
        this.dumpEntryList= -1;
        this.formationLapType= -1;
        this.ignorePrematureDisconnects= -1;
        this.imageContent= '';
    }
}