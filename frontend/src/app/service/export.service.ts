import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {handleError} from "../util/Error.handler";
import {catchError, tap} from "rxjs/operators";
import {Race} from "../model/Race";

@Injectable()
export class ExportService {

    private url: string = environment.apiPath + 'export';

    constructor(
      private httpClient: HttpClient
    ){}

    exportData(race: Race, errorNotify?: any) {
        return this.httpClient.post<Race>(this.url, race).pipe(
            tap(response => console.log('exported data')),
            catchError(handleError('ExportService -> exportData', null, errorNotify))
        )
    }

}