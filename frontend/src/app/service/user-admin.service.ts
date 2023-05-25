import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { User } from '../model/User';
import { tap, catchError } from 'rxjs/operators';
import { handleError } from '../util/Error.handler';

@Injectable()
export class UserAdminService {

    private url: string = environment.apiPath + 'admin/user/';

    constructor(
        private httpClient: HttpClient
        ) {
    }

    createUser(user: User, errorNotify?: any) {
        return this.httpClient.post<User>(this.url, user)
            .pipe(
                tap(user => console.log('fetched user created')),
                catchError(handleError('UserAdminService -> createUser', null, errorNotify))
            );
    }

    updateUser(user: User, errorNotify?: any) {
        return this.httpClient.put<User>(this.url, user)
            .pipe(
                tap(user => console.log('fetched user updated')),
                catchError(handleError('UserAdminService -> updateUser', null, errorNotify))
            );
    }

    deleteUser(userId: Number, errorNotify?: any) {
        const url = this.url + '/' + userId;
        return this.httpClient.delete<User>(url)
            .pipe(
                tap(user => console.log('fetched user deleted')),
                catchError(handleError('UserAdminService-> deleteUser', null, errorNotify))
            );
    }

    setAdmin(userId: Number, set: boolean, errorNotify?: any) {
        const url = this.url + 'admin/' + userId;
        return this.httpClient.put<User>(url, {setValue: set})
            .pipe(
                tap(user => console.log('fetched set admin')),
                catchError(handleError('UserAdminService -> setAdmin', null, errorNotify))
            );
    }
}
