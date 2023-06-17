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
    }
}