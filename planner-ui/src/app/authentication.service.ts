import { Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { environment } from '../environments/environment';
import { ErrorService } from './error.service';

const TOKEN_KEY = 'authToken';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private rootUrl = environment.plannerBackendRootUrl;

  authenticated = false;
  authToken: string;
  csrfCookie: string;

  constructor(private http: HttpClient, private errors: ErrorService) {
    const token = localStorage.getItem(TOKEN_KEY);
    if (token) {
      this.authenticated = true;
      this.authToken = token;
    }
  }

  private getCsrfCookie(): string {
    const tab = document.cookie.split(';');
    if (tab.length > 0) {
      const tab1 = tab[0].split('=');
      if (tab1.length > 1) {
        return tab1[1];
      }
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
          this.csrfCookie = this.getCsrfCookie();
        } else {
          this.authenticated = false;
          this.authToken = null;
          localStorage.clear();
          this.csrfCookie = null;
        }
        return callback && callback();
      });
  }

  logout() {
    this.http.post(this.rootUrl + `/logout?_csrf=${this.csrfCookie}`, {}, {
      responseType: 'text',
      headers: new HttpHeaders({
        authorization: this.authToken
      }),
      withCredentials: true
    })
    .pipe(
      catchError(this.handleError('logout', null))
    )
    .subscribe(response => {
      this.authenticated = false;
      this.authToken = null;
      localStorage.clear();
      this.csrfCookie = null;
    });
  }

  private handleError<T>(operation = 'operation', result ?: T) {
    return (error: any): Observable<T> => {
      this.authenticated = false;
      this.authToken = null;
      this.csrfCookie = null;
      localStorage.clear();
      this.errors.addError(`${operation} failed! Show Jim this error!`);
      console.error(error);
      return of(result as T);
    };
  }
}
