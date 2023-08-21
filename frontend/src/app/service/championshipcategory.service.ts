import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {handleError} from "../util/Error.handler";
import {catchError, tap} from "rxjs/operators";

@Injectable()
export class ChampionshipCategoryService {
    private url: string= environment.apiPath + 'category';

    constructor(
        private httpClient: HttpClient
    ) {
    }

    getCategoryByChampionshipId(id: number, errorNotify?) {
        const url = this.url + "/" + id;
        return this.httpClient.get<string>(url).pipe(
            tap(user => console.log('fetched Categories by Championship Id')),
            catchError(handleError('ChampionshipCategoryService -> getCategoryByChampionshipId', null, errorNotify)))
    }

}