export class User {

    userId: Number;
    steamId: String;
    firstName: String;
    lastName: String;
    shortName: String;
    nationality: String;

    constructor() {
        this.userId = -1;
        this.steamId = '';
        this.firstName = '';
        this.lastName = '';
        this.shortName = '';
        this.nationality = '';
    }
}
