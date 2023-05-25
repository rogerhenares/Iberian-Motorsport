import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {catchError, tap} from "rxjs/operators";
import {handleError} from "../util/Error.handler";
import {Page} from "../model/Page";
import {RaceRules} from "../model/RaceRules";

@Injectable()
export class RaceRulesService {

    private url: string = environment.apiPath + 'race-rules/';

    constructor(
        private httpClient: HttpClient
    ) {
    }

    saveRaceRules(raceRules: RaceRules, errorNotify?: any) {
        return this.httpClient.post<RaceRules>(this.url, raceRules)
            .pipe(
                tap(user => console.log('save RaceRules info')),
                catchError(handleError('RaceRulesService -> saveRaceRules', null, errorNotify))
            );
    }

    updateRaceRules(raceRules: RaceRules, errorNotify?: any) {
        return this.httpClient.put<RaceRules>(this.url, raceRules)
            .pipe(
                tap(user => console.log('update RaceRules info')),
                catchError(handleError('RaceRulesService -> updateRaceRules', null, errorNotify))
            );
    }

    getRaceRulesById(raceRulesId: Number, errorNotify?: any) {
        const url = this.url + raceRulesId;
        return this.httpClient.get<RaceRules>(url)
            .pipe(
                tap(user => console.log('fetched RaceRules by raceRulesId')),
                catchError(handleError('RaceRulesService -> findRaceRulesById', null, errorNotify))
            );
    }

    getRaceRulesList(page: Number, errorNotify?: any) {
        let url = this.url + '/list/?';
        if (page) {
            url += 'page=' + page;
        }
        return this.httpClient.get<Page>(url)
            .pipe(
                tap(response => console.log('fetched RaceRules page')),
                catchError(handleError('RaceRulesService -> findAllRaceRules', null, errorNotify))
            );
    }

    deleteRace(raceRulesId: Number, errorNotify?: any) {
        let url = this.url + raceRulesId;
        return this.httpClient.delete<RaceRules>(url)
            .pipe(
                tap(response => console.log('delete RaceRules')),
                catchError(handleError('RaceRulesService -> deleteRaceRules', null, errorNotify))
            );
    }
}