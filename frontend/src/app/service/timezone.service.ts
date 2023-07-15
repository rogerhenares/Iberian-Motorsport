import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class TimezoneService {
    userTimezone: string;

    constructor() {
        this.userTimezone = Intl.DateTimeFormat().resolvedOptions().timeZone;
    }

    convertDateToUserTimezone(date: string): string {
        return new Date(date).toLocaleString('en-US', {
            timeZone: this.userTimezone,
            dateStyle: 'full',
            timeStyle: 'short',
        });
    }

}
