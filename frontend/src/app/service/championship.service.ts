import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { catchError, tap } from 'rxjs/operators';
import { Championship } from '../model/Championship';
import { handleError } from '../util/Error.handler';
import { environment } from '../../environments/environment';
import { Page } from '../model/Page';
import {Pageable} from "../model/Pageable";

@Injectable()
export class ChampionshipService {

    private url: string = environment.apiPath + 'championship';

    constructor(
        private httpClient: HttpClient
    ) {
    }

    saveChampionship(championship: Championship, errorNotify?: any) {
        return this.httpClient.post<Championship>(this.url, championship)
            .pipe(
                tap(user => console.log('save Championship info')),
                catchError(handleError('ChampionshipService -> saveChampionship', null, errorNotify))
            );
    }

    updateChampionship(championship: Championship, errorNotify?: any) {
        return this.httpClient.put<Championship>(this.url, championship)
            .pipe(
                tap(user => console.log('update Championship info')),
                catchError(handleError('ChampionshipService -> updateChampionship', null, errorNotify))
            );
    }

    getChampionshipById(championshipId: Number, errorNotify?: any) {
        const url = this.url + '/' + championshipId;
        return this.httpClient.get<Championship>(url)
            .pipe(
                tap(user => console.log('fetched Championship by championshipId')),
                catchError(handleError('ChampionshipService -> findChampionshipById', null, errorNotify))
            );
    }

    getChampionshipByName(championshipName: String, errorNotify?: any) {
        const url = this.url + '/name/' + championshipName;
        return this.httpClient.get<Championship>(url)
            .pipe(
                tap(user => console.log('fetched Championship by name')),
                catchError(handleError('ChampionshipService -> findChampionshipByName', null, errorNotify))
            );
    }

    getChampionshipList(pageable: Pageable, errorNotify?: any) {
        let url = this.url + "?page=" + pageable.page + "&size=" + pageable.size;
        return this.httpClient.get<Page>(url)
            .pipe(
                tap(response => console.log('fetched Championship page')),
                catchError(handleError('ChampionshipService -> findAllChampionships', null, errorNotify))
            );
    }

    deleteChampionship(championshipId: Number, errorNotify?: any) {
        let url = this.url + championshipId;
        return this.httpClient.delete<Championship>(url)
            .pipe(
                tap(response => console.log('delete Championship')),
                catchError(handleError('ChampionshipService -> deleteChampionship', null, errorNotify))
            );
    }
}