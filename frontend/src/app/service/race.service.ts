import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {catchError, tap} from "rxjs/operators";
import {handleError} from "../util/Error.handler";
import {Race} from "../model/Race";
import {Page} from "../model/Page";
import {RaceRules} from "../model/RaceRules";

@Injectable()
export class RaceService {

    private url: string = environment.apiPath + 'race/';

    constructor(
        private httpClient: HttpClient
    ) {
    }

    saveRace(race: Race, errorNotify?: any) {
        return this.httpClient.post<Race>(this.url, race)
            .pipe(
                tap(user => console.log('save Race info')),
                catchError(handleError('RaceService -> saveRace', null, errorNotify))
            );
    }

    updateRace(race: Race, errorNotify?: any) {
        return this.httpClient.put<Race>(this.url, race)
            .pipe(
                tap(user => console.log('update Race info')),
                catchError(handleError('RaceService -> updateRace', null, errorNotify))
            );
    }

    getRaceById(raceId: Number, errorNotify?: any) {
        const url = this.url + raceId;
        return this.httpClient.get<Race>(url)
            .pipe(
                tap(user => console.log('fetched Race by raceId')),
                catchError(handleError('RaceService -> findRaceById', null, errorNotify))
            );
    }

    getRaceByName(raceName: String, errorNotify?: any) {
        const url = this.url + '/name' +raceName;
        return this.httpClient.get<Race>(url)
            .pipe(
                tap(user => console.log('fetched Race by raceName')),
                catchError(handleError('RaceService -> findRaceByName', null, errorNotify))
            );
    }

    getRaceList(page: Number, errorNotify?: any) {
        let url = this.url + '/list/?';
        if (page) {
            url += 'page=' + page;
        }
        return this.httpClient.get<Page>(url)
            .pipe(
                tap(response => console.log('fetched Race page')),
                catchError(handleError('RaceService -> findAllRaces', null, errorNotify))
            );
    }

    deleteRace(raceId: Number, errorNotify?: any) {
        let url = this.url + raceId;
        return this.httpClient.delete<Race>(url)
            .pipe(
                tap(response => console.log('delete Race')),
                catchError(handleError('RaceService -> deleteRace', null, errorNotify))
            );
    }
}