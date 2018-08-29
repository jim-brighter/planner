import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { environment } from '../environments/environment';
import { ErrorService } from './error.service';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';

const TOKEN_KEY = 'authToken';
const CSRF_KEY = 'csrfToken';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private rootUrl = environment.plannerBackendRootUrl;
  private rootAuthUrl = environment.plannerAuthBackend;

  authenticated = false;
  authToken: string;
  csrfCookie: string;

  constructor(private http: HttpClient, private errors: ErrorService, private router: Router) {
    const tokenAuth = localStorage.getItem(TOKEN_KEY);
    const tokenCsrf = localStorage.getItem(CSRF_KEY);
    if (tokenAuth && tokenCsrf) {
      this.authenticated = true;
      this.authToken = tokenAuth;
      this.csrfCookie = tokenCsrf;
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

    this.http.get(this.rootAuthUrl + '/token', { headers: headers, withCredentials: true })
      .pipe(
        catchError(this.handleError('authenticate', null))
      )
      .subscribe(response => {
        if (response && response['token']) {
          this.authenticated = true;
          this.authToken = response['token'];
          localStorage.setItem(TOKEN_KEY, this.authToken);
          this.csrfCookie = this.getCsrfCookie();
          localStorage.setItem(CSRF_KEY, this.csrfCookie);
          this.errors.clear();
        } else {
          this.wipeSession();
        }
        return callback && callback();
      });
  }

  logout() {
    this.http.post(this.rootAuthUrl + `/logout?_csrf=${this.csrfCookie}`, {}, {
      responseType: 'text',
      headers: new HttpHeaders({
        'X-Auth-Token': this.authToken,
        'X-Xsrf-Token': this.csrfCookie
      }),
      withCredentials: true
    })
    .pipe(
      catchError(this.handleError('logout', null))
    )
    .subscribe(response => {
      this.wipeSession();
    });
  }

  private handleError<T> (operation = 'operation', result ?: T) {
    this.wipeSession();
    return this.errors.handleError(operation, result);
  }

  private wipeSession() {
    this.authenticated = false;
    this.authToken = null;
    this.csrfCookie = null;
    localStorage.clear();
  }

}
