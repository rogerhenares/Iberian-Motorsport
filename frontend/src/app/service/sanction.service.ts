import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Sanction} from "../model/Sanction";
import {catchError, tap} from "rxjs/operators";
import {handleError} from "../util/Error.handler";

@Injectable()
export class SanctionService {
    private url: string = environment.apiPath + 'sanction';

    constructor(
        private httpClient: HttpClient
    ) {
    }

    createSanction(sanction: Sanction, errorNotify?: any) {
        return this.httpClient.post<Sanction>(this.url, sanction)
            .pipe(
                tap(sanction => console.log('Saved Sanction')),
                catchError(handleError('SanctionService -> createSanction', null, errorNotify))
            )
    }

    getSanctionList(raceId: Number, errorNotify?: any){
        const  url: string= this.url + '/race/' + raceId;
        return this.httpClient.get<Sanction[]>(url)
            .pipe(
                tap(sanction => console.log('Sanction retrieved')),
                catchError(handleError('SanctionService -> getSanctionByRaceId', null, errorNotify))
            )
    }

    deleteSanction(sanctionId: Number, errorNotify?: any){
        const  url: string= this.url + '/delete/' + sanctionId;
        return this.httpClient.delete<Sanction>(url)
            .pipe(
                tap(sanction => console.log('Sanction deleted')),
                catchError(handleError('SanctionService -> deleteSanction', null, errorNotify))
            )
    }

}