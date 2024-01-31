import { Observable, of } from 'rxjs';
import { SwalComponent } from '@sweetalert2/ngx-sweetalert2';
import { ErrorBoolean } from '../model/ErrorBoolean';

/**
 * Handle Http operation that failed.
 * Let the app continue.
 * @param operation - name of the operation that failed
 * @param result - optional value to return as the observable result
 * @Param errorNotify - errorNotify
 */
export function handleError<T> (operation = 'operation', result: T, errorNotify?: any) {
    return (error: any): Observable<T> => {
        if (errorNotify instanceof ErrorBoolean) {
            errorNotify.show();
        }
        if (errorNotify instanceof Function) {
            errorNotify();
        }
        if (errorNotify instanceof SwalComponent) {
            if (error.status === 409) {
                errorNotify.title = error.error;
            } else {
                errorNotify.title = error.error;
            }
            errorNotify.fire();
        }
        if (errorNotify === true) {
            result = error.error.error;
        }
        // Let the app keep running by returning an empty result.
        return of(result as T);
    };
}
