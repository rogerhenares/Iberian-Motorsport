import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Grid} from "../model/Grid";
import {catchError, tap} from "rxjs/operators";
import {handleError} from "../util/Error.handler";
import {Observable} from "rxjs";

@Injectable()
export class GridService {

    private url: string = environment.apiPath + 'grid';

    constructor(
        private httpClient: HttpClient
    ) {
    }

    createGridEntry(grid: Grid, errorNotify?: any){
        return this.httpClient.post<Grid>(this.url, grid)
            .pipe(
                tap(grid=>console.log('save Grid Entry')),
                catchError(handleError('GridService -> createGridEntry', null, errorNotify))
            )
    }

    updateGridEntry(grid: Grid, errorNotify?: any) {
        return this.httpClient.put<Grid>(this.url, grid)
            .pipe(
                tap(grid=> console.log('update Grid info')),
                catchError(handleError('GridService -> updateGridEntry', null, errorNotify))
            );
    }

    addDriver(grid: Grid, steamId: number, errorNotify?: any){
        const url: string = this.url + '/add/' + grid.id;
        const body = { steamId: steamId };
        return this.httpClient.put<Grid>(url, body)
            .pipe(
                tap( grid=> console.log('added driver')),
                catchError(handleError('GridService -> addDriver', null, errorNotify))
        )
    }

    removeDriver(grid: Grid, steamId: Number, errorNotify?: any){
        const url: string = this.url + '/remove/' + grid.id;
        const options = {
            params: new HttpParams().set('steamId', steamId.toString())
        };
        return this.httpClient.delete<Grid>(url, options)
            .pipe(
                tap( grid=> console.log('removed driver')),
                catchError(handleError('GridService -> removeDriver', null, errorNotify))
            )
    }

    getGridForChampionship(championshipId: Number): Observable<Grid[]> {
        const url = this.url + "/public/" + championshipId;
        return this.httpClient.get<Grid[]>(url);
    }

}
