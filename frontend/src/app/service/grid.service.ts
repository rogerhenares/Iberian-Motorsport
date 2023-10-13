import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Grid} from "../model/Grid";
import {catchError, tap} from "rxjs/operators";
import {handleError} from "../util/Error.handler";
import {Observable} from "rxjs";
import {response} from "express";

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
        return this.httpClient.put<Grid>(this.url + "/" + grid.id, grid)
            .pipe(
                tap(grid=> console.log('update Grid info')),
                catchError(handleError('GridService -> updateGridEntry', null, errorNotify))
            );
    }

    addDriver(grid: Grid, steamId: string, errorNotify?: any){
        const url: string = this.url + '/add/' + grid.id + '/driver/' + steamId;
        return this.httpClient.put<Grid>(url, grid)
            .pipe(
                tap( grid=> console.log('added driver')),
                catchError(handleError('GridService -> addDriver', null, errorNotify))
        )
    }

    removeDriver(grid: Grid, steamId: string, errorNotify?: any){
        const url: string = this.url + '/remove/' + grid.id + '/driver/' + steamId;
        return this.httpClient.delete<Grid>(url)
            .pipe(
                tap( grid=> console.log('removed driver')),
                catchError(handleError('GridService -> removeDriver', null, errorNotify))
            )
    }

    getGridForChampionship(championshipId: Number): Observable<Grid[]> {
        const url = this.url + "/public/" + championshipId;
        return this.httpClient.get<Grid[]>(url);
    }

    getGridByPassword(password: String): Observable<Grid> {
        const url = this.url + "/public/password/" + password;
        return this.httpClient.get<Grid>(url);
    }

    deleteGrid(gridId: Number, errorNotify?: any) {
        let url = this.url + "/delete/" + gridId;
        return this.httpClient.delete<Grid>(url)
            .pipe(
                tap(response => console.log('deleted Grid')),
                catchError(handleError('GridService -> deleteGrid', null, errorNotify))
            )
    }

}
