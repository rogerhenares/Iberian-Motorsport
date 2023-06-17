export class Session {
    id: Number;
    hourOfDay: Number;
    dayOfWeekend: Number;
    timeMultiplier: Number;
    sessionType: String;
    sessionDurationMinutes: Number;

    constructor() {
        this.id = null;
        this.hourOfDay= null;
        this.dayOfWeekend= null;
        this.timeMultiplier= null;
        this.sessionType= '';
        this.sessionDurationMinutes= null;
    }
}