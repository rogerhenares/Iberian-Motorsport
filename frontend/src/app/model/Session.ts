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
        this.hourOfDay= null;
        this.dayOfWeekend= null;
        this.timeMultiplier= null;
        this.sessionType= '';
        this.sessionDurationMinutes= null;
        this.raceId = null;
    }
}