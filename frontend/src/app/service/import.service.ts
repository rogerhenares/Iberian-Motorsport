import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Race} from "../model/Race";
import {catchError, tap} from "rxjs/operators";
import {handleError} from "../util/Error.handler";

@Injectable()
export class ImportService {

    private url: string = environment.apiPath + 'import';
    constructor(
        private httpClient: HttpClient
    ) {
    }

    importData(race: Race, errorNotify?: any) {
        return this.httpClient.post(this.url, race).pipe(
            tap(response => console.log('imported data')),
            catchError(handleError('ImportService -> importData', null, errorNotify))
        )
    }

}