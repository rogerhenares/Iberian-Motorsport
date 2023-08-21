export class User {

    userId: number;
    steamId: string;
    firstName: string;
    lastName: string;
    shortName: string;
    nationality: string;
    roleList: Array<string>

    constructor() {
        this.userId = -1;
        this.steamId = '';
        this.firstName = '';
        this.lastName = '';
        this.shortName = '';
        this.nationality = '';
        this.roleList = [];
    }
}
