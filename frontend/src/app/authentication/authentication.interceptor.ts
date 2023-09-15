import { Injectable } from '@angular/core';
import {HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse} from '@angular/common/http';
import { AppContext } from '../util/AppContext';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {

    constructor(public appContext: AppContext) { }


    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const headers = req.headers;
        const headerAuthorization = headers.get('Authorization');
        if(this.appContext.authenticationInfo.authorizationToken) {
            if (headerAuthorization == null) {
                req = req.clone({
                    setHeaders: {
                        Authorization: this.appContext.authenticationInfo.authorizationToken
                    }
                });
            }
        }
        return next.handle(req).pipe(
            retry(0),
            catchError((error: HttpErrorResponse) => {
                if (error instanceof HttpErrorResponse && error.status === 403) {
                    this.appContext.clearUser();
                    //window.location.href = error.headers.get("Location");
                }
                return throwError(error);
            })
        );
    }
}
