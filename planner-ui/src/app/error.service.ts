import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  private errorMessages: Array<string> = new Array<string>();

  constructor() { }

  getErrors(): Array<string> {
    return this.errorMessages;
  }

  addError(errorMessage: string): void {
    this.errorMessages.push(errorMessage);
  }

  clear(): void {
    this.errorMessages = [];
  }

  handleError<T> (operation = 'operation', result ?: T) {
    return (error: any): Observable<T> => {
      console.log(error);
      let message = `${operation} failed! Show Jim this error!`;
      if (error.status === 403) {
        message = 'Your session is invalid. Please try logging in again.';
      }
      this.addError(message);
      return of(result as T);
    };
  }
}
