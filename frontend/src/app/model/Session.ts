export class Session {
    id: Number;
    hourOfDay: Number;
    dayOfWeekend: Number;
    timeMultiplier: Number;
    sessionType: String;
    sessionDurationMinutes: Number;

    constructor() {
        this.id = -1;
        this.hourOfDay= -1;
        this.dayOfWeekend= -1;
        this.timeMultiplier= -1;
        this.sessionType= '';
        this.sessionDurationMinutes= -1;
    }
}