import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { tap, catchError } from 'rxjs/operators';
import { handleError } from '../util/Error.handler';
import { Process } from '../model/Process';

@Injectable()
export class ProcessService {

    private url: string = environment.apiPath + 'process/';

    constructor(
        private httpClient: HttpClient
        ) {
    }

    getProcessTraining(errorNotify?: any) {
        const url = this.url + 'training';
        return this.httpClient.get<Process>(url)
            .pipe(
                tap(user => console.log('fetched process training')),
                catchError(handleError('ProcessService -> getProcessTraining', null, errorNotify))
            );
    }

    getProcessTest(errorNotify?: any) {
        const url = this.url + 'test';
        return this.httpClient.get<Process>(url)
            .pipe(
                tap(user => console.log('fetched process test')),
                catchError(handleError('ProcessService -> getProcessTest', null, errorNotify))
            );
    }
}
