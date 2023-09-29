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
        this.hourOfDay= 14;
        this.dayOfWeekend= null;
        this.timeMultiplier= null;
        this.sessionType= '';
        this.sessionDurationMinutes= null;
        this.raceId = null;
    }

    // TODO P 10 minutos, Q 21 minutos y R 61 minutos

}