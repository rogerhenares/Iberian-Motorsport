import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {GridRace} from "../model/GridRace";

@Injectable()
export class GridRaceService {
    private url: string= environment.apiPath + 'grid-race';

    constructor(
        private httpClient: HttpClient
    ) {
    }

    getGridRace(gridId: number, raceId: number): Observable<GridRace> {
        const url = this.url + "/" + gridId + "/" + raceId;
        return this.httpClient.get<GridRace>(url);
    }

    getGridRaceForRace(raceId: number): Observable<GridRace[]> {
        const url = this.url + "/public/" + raceId;
        return this.httpClient.get<GridRace[]>(url);
    }

}
