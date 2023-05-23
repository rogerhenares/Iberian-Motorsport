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
        console.log('TCL: error', error);
        console.log("🚀 ~ file: Error.handler.ts ~ line 16 ~ return ~ errorNotify", errorNotify)
        if (errorNotify instanceof ErrorBoolean) {
            errorNotify.show();
        }
        if (errorNotify instanceof Function) {
            errorNotify();
        }
        if (errorNotify instanceof SwalComponent) {
            if (error.status === 409) {
                errorNotify.text = error.error.error.message + ', error code: ' + error.error.error.platform_error_code;
            } else {
                errorNotify.text = error.statusText;
            }
            errorNotify.fire();
        }
        if (errorNotify === true) {
            console.log('TLC: error-> result', error)
            result = error.error.error;
        }
        // Let the app keep running by returning an empty result.
        return of(result as T);
    };
}