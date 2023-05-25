export class User {

    userId: Number;
    steamId: Number;
    firstName: String;
    lastName: String;
    shortName: String;
    nationality: String;

    constructor() {
        this.userId = -1;
        this.steamId = -1;
        this.firstName = '';
        this.lastName = '';
        this.shortName = '';
        this.nationality = '';
    }
}
