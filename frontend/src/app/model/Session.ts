export class Session {
    id: number;
    hourOfDay: number;
    dayOfWeekend: number;
    timeMultiplier: number;
    sessionType: string;
    sessionDurationMinutes: number;
    raceId: number;

    constructor() {
        this.id = null;
        this.hourOfDay= 12;
        this.dayOfWeekend= null;
        this.timeMultiplier= null;
        this.sessionType= '';
        this.sessionDurationMinutes= null;
        this.raceId = null;
    }

    static defaultPractice(): Session {
        let practice = new Session();
        practice.dayOfWeekend = 1;
        practice.timeMultiplier = 1;
        practice.sessionType = "P";
        practice.sessionDurationMinutes = 10;
        return practice;
    }

    static defaultQualy(): Session {
        let qualy = new Session();
        qualy.dayOfWeekend = 2;
        qualy.timeMultiplier = 1;
        qualy.sessionType = "Q";
        qualy.sessionDurationMinutes = 21;
        return qualy;
    }

    static defaultRace(): Session {
        let race = new Session();
        race.dayOfWeekend = 3;
        race.timeMultiplier = 1;
        race.sessionType = "R";
        race.sessionDurationMinutes = 63;
        return race;
    }

}
