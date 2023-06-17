import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {catchError, tap} from "rxjs/operators";
import {handleError} from "../util/Error.handler";
import {Page} from "../model/Page";
import {Session} from "../model/Session";


@Injectable()
export class SessionService {

    private url: string = environment.apiPath + 'session/';

    constructor(
        private httpClient: HttpClient
    ) {
    }

    saveSession(session: Session, errorNotify?: any) {
        return this.httpClient.post<Session>(this.url, session)
            .pipe(
                tap(user => console.log('save Session info')),
                catchError(handleError('SessionService -> saveSession', null, errorNotify))
            );
    }


    updateSession(session: Session, errorNotify?: any) {
        return this.httpClient.put<Session>(this.url, session)
            .pipe(
                tap(user => console.log('update Session info')),
                catchError(handleError('SessionService -> updateSession', null, errorNotify))
            );
    }

    getSessionById(sessionId: Number, errorNotify?: any) {
        const url = this.url + sessionId;
        return this.httpClient.get<Session>(url)
            .pipe(
                tap(user => console.log('fetched Session by SessionId')),
                catchError(handleError('SessionService -> findSessionById', null, errorNotify))
            );
    }

    getSessionList(page: Number, errorNotify?: any) {
        let url = this.url + '/list/?';
        if (page) {
            url += 'page=' + page;
        }
        return this.httpClient.get<Page>(url)
            .pipe(
                tap(response => console.log('fetched Session page')),
                catchError(handleError('SessionService -> findAllSessions', null, errorNotify))
            );
    }

    deleteRace(sessionId: Number, errorNotify?: any) {
        let url = this.url + sessionId;
        return this.httpClient.delete<Session>(url)
            .pipe(
                tap(response => console.log('delete Session')),
                catchError(handleError('SessionService -> deleteSession', null, errorNotify))
            );
    }
}