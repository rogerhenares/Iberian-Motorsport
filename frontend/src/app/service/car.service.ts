import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Car} from "../model/Car";
import {catchError, tap} from "rxjs/operators";
import {handleError} from "../util/Error.handler";
import {error} from "protractor";


class List<T> {
}

@Injectable()
export class CarService {
    private url: string = environment.apiPath + 'car';

    constructor(
        private httpClient: HttpClient
    ) {
    }

    getCarsByCategories(category: Array<string>, errorNotify?){
        const url = this.url + "/category/" + category
        return this.httpClient.get<Car[]>(url).pipe(
            tap(user => console.log('fetched Cars by category')),
                catchError(handleError('CarService -> getCarsByCategories', null, errorNotify))
        )
    }

    getCarByModel(model: string, errorNotify?) {
        const url = this.url + "/model/" + model
        return this.httpClient.get<Car>(url).pipe(
            tap(car => console.log('fetched car by model')),
            catchError(handleError('CarService -> getCarByModel', null, errorNotify))
        )
    }

    getCarById(carId: number, errorNotify?) {
        const url = this.url + "/id/" + carId
        return this.httpClient.get<Car>(url).pipe(
            tap(car => console.log('fetched car by id')),
            catchError(handleError('CarService -> getCarById', null, errorNotify ))
        )
    }

}