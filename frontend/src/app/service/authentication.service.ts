import { Injectable, ErrorHandler } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

import { UserService } from './user.service';
import { environment } from '../../environments/environment';
import { AppContext } from '../util/AppContext';
import { handleError } from '../util/Error.handler';

@Injectable()
export class AuthenticationService {

    constructor(
        private appContext: AppContext,
        private httpClient: HttpClient) { }

    authenticate(username: string, password: string, errorNotify?: any): Observable<any> {
    const url = environment.apiPath + 'oauth/token';
    return this.httpClient.post(url, 'grant_type=password&username=' +
        encodeURIComponent(username) + '&password=' + encodeURIComponent(password), {
            headers: new HttpHeaders()
            .set('Content-Type', 'application/x-www-form-urlencoded')
            .set('Authorization', 'Basic ' + environment.clientId)
        }
    ).pipe(
        tap(token => this.saveAuthenticationInfo(token)),
        catchError(handleError('AuthenticationService -> authenticate', null, errorNotify)));
    }

    private saveAuthenticationInfo(token) {
        const authenticationInfo = this.appContext.authenticationInfo;
        authenticationInfo.authorizationToken = token['access_token'];
        authenticationInfo.expiresIn = token['expires_in'];
        authenticationInfo.refreshToken = token['refresh_token'];
        authenticationInfo.save();
    }

}
