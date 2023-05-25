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
    }
}