import { Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { environment } from '../environments/environment';
import { ErrorService } from './error.service';
import { Router } from '@angular/router';

const TOKEN_KEY = 'authToken';
const CSRF_KEY = 'csrfToken';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private rootUrl = environment.plannerBackendRootUrl;

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
    console.log(`cookie: ${document.cookie}`);
    const tab = document.cookie.split(';');
    if (tab.length > 0) {
      const tab1 = tab[0].split('=');
      if (tab1.length > 1) {
        console.log(`csrf token: ${tab1[1]}`);
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
        catchError(this.errors.handleError('authenticate', null))
      )
      .subscribe(response => {
        if (response && response['name']) {
          console.log('auth success');
          this.authenticated = true;
          this.authToken = headers.get('Authorization');
          localStorage.setItem(TOKEN_KEY, this.authToken);
          this.csrfCookie = this.getCsrfCookie();
          localStorage.setItem(CSRF_KEY, this.csrfCookie);
          this.errors.clear();
        } else {
          console.log('auth fail');
          this.wipeSession();
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
      catchError(this.errors.handleError('logout', null))
    )
    .subscribe(response => {
      this.wipeSession();
    });
  }

  private wipeSession() {
    console.log('wiping session');
    this.authenticated = false;
    this.authToken = null;
    this.csrfCookie = null;
    localStorage.clear();
  }

}
