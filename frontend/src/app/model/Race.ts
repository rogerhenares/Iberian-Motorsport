export class Race {
    id: Number;
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

    constructor() {
        this.id= -1;
        this.track= '';
        this.preRaceWaitingTimeSeconds= -1;
        this.sessionOverTimeSeconds= -1;
        this.ambientTemp= -1;
        this.cloudLevel= -1;
        this.rain= -1;
        this.weatherRandomness= -1;
        this.postQualySeconds= -1;
        this.postRaceSeconds= -1;
        this.serverName= '';
    }
}