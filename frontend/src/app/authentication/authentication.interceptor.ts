import { Injectable } from '@angular/core';
import {HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse} from '@angular/common/http';
import { Router } from '@angular/router';
import { AppContext } from '../util/AppContext';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {

    constructor(
        private appContext: AppContext,
        private router: Router) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const headers = req.headers;
        const headerAuthorization = headers.get('Authorization');
        if (headerAuthorization == null) {
            req = req.clone({
                setHeaders: {
                    Authorization: 'Bearer ' + this.appContext.authenticationInfo.authorizationToken
                }
            });
        }
        return next.handle(req).pipe(
            retry(0),
            catchError((error: HttpErrorResponse) => {
                if (error instanceof HttpErrorResponse && error.status === 401) {
                    // handle 401 errors
                    this.appContext.clearUser();
                    this.router.navigateByUrl('/login');
                }
                return throwError(error);
            })
        );
    }
}
