import { Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { environment } from '../environments/environment';

const TOKEN_KEY = 'authToken';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private rootUrl = environment.plannerBackendRootUrl;

  authenticated = false;
  authToken: string;
  errorMessages: Array<string> = new Array<string>();

  constructor(private http: HttpClient) {
    const token = localStorage.getItem(TOKEN_KEY);
    if (token) {
      this.authenticated = true;
      this.authToken = token;
    }
  }

  authenticate(credentials, callback) {
    const headers = new HttpHeaders(credentials ? {
      authorization: 'Basic ' + btoa(credentials.username + ':' + credentials.password)
    } : {});

    this.http.get(this.rootUrl + '/user', { headers: headers })
      .pipe(
        catchError(this.handleError('authenticate', null))
      )
      .subscribe(response => {
        if (response && response['name']) {
          this.authenticated = true;
          this.authToken = headers.get('Authorization');
          localStorage.setItem(TOKEN_KEY, this.authToken);
        } else {
          this.authenticated = false;
          this.authToken = null;
          localStorage.clear();
        }
        return callback && callback();
      });
  }

  private handleError<T>(operation = 'operation', result ?: T) {
    return (error: any): Observable<T> => {
      this.authenticated = false;

      if (error.status === 401) {
        this.errorMessages.push('Failed to authenticate');
      } else {
        console.error(error);
        alert(`${operation} failed - check the console for the error`);
      }

      return of(result as T);
    };
  }
}
