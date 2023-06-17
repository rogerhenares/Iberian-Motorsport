import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, tap } from 'rxjs/operators';
import { User } from '../model/User';
import { handleError } from '../util/Error.handler';
import { environment } from '../../environments/environment';
import { Page } from '../model/Page';
import {AppContext} from "../util/AppContext";

@Injectable()
export class UserService {

    private url: string = environment.apiPath + 'user/';

    constructor(
        private httpClient: HttpClient,
        private appContext: AppContext
        ) {
    }

    getLoggedUser(errorNotify?: any) {
        return this.httpClient.get<User>(this.url + 'loggedUser')
            .pipe(
                tap(user => console.log('fetched logged user info')),
                catchError(handleError('UserService -> getLoggedUser', null, errorNotify))
            );
    }

    updateUserInfo(user: User, errorNotify?: any) {
        return this.httpClient.put<User>(this.url, user)
            .pipe(
                tap(user => console.log('fetched update user info')),
                catchError(handleError('UserService -> updateUserInfo', null, errorNotify))
            );
    }

    getUserByUserId(userId: Number, errorNotify?: any) {
        const url = this.url + userId;
        return this.httpClient.get<User>(url)
            .pipe(
                tap(user => console.log('fetched user by userId')),
                catchError(handleError('UserService -> getUserByUserId', null, errorNotify))
            );
    }

    getUserByName(userName: String, errorNotify?: any) {
        const url = this.url + '/name/' + userName;
        return this.httpClient.get<User>(url)
            .pipe(
                tap(user => console.log('fetched user by name')),
                catchError(handleError('UserService -> getUserByName', null, errorNotify))
            );
    }

    getUserList(page: Number, errorNotify?: any) {
        let url = this.url + '/list/?';
        if (page) {
            url += 'page=' + page;
        }
        return this.httpClient.get<Page>(url)
            .pipe(
                tap(response => console.log('fetched user page')),
                catchError(handleError('UserService -> getUserList', null, errorNotify))
            );
    }

    deleteUser(userId: Number, errorNotify?: any) {
        let url = this.url + userId;
        return this.httpClient.delete<User>(url)
            .pipe(
                tap(response => console.log('delete user')),
                catchError(handleError('UserService -> deleteUser', null, errorNotify))
            );
    }

    getUser() {

    }
}
