import { Injectable, ErrorHandler } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

import { environment } from '../../environments/environment';
import { AppContext } from '../util/AppContext';
import { handleError } from '../util/Error.handler';

@Injectable()
export class AuthenticationService {

    constructor(
        private appContext: AppContext,
        private httpClient: HttpClient) { }

    authenticate(steamPath: string, errorNotify?: any): Observable<any> {
    const url = environment.apiPath + 'public/login?steamEncoded='+steamPath;
    return this.httpClient.get(url).pipe(
        tap(token => this.saveAuthenticationInfo(token)),
        catchError(handleError('AuthenticationService -> authenticate', null, errorNotify)));
    }

    private saveAuthenticationInfo(token) {
        const authenticationInfo = this.appContext.authenticationInfo;
        authenticationInfo.authorizationToken = token.token;
        authenticationInfo.save();
    }

}
