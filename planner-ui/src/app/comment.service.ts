import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { environment } from '../environments/environment';
import { Comment } from './comment';
import { AuthenticationService } from './authentication.service';
import { ErrorService } from './error.service';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private rootUrl = environment.plannerBackendRootUrl;
  private apiContext = environment.plannerBackendCommentsContext;

  private postHttpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'X-Auth-Token': this.auth.authToken,
      'X-Xsrf-Token': this.auth.csrfCookie
    }),
    withCredentials: true
  };

  private getHttpOptions = {
    headers: new HttpHeaders({
      'X-Auth-Token': this.auth.authToken,
      'X-Xsrf-Token': this.auth.csrfCookie
    }),
    withCredentials: true
  };

  constructor(private http: HttpClient, private auth: AuthenticationService, private errors: ErrorService) { }

  getComments(): Observable<Comment[]> {
    return this.http.get<Comment[]>(this.rootUrl + this.apiContext, this.getHttpOptions)
      .pipe(
        catchError(this.errors.handleError('getComments', new Array<Comment>()))
      );
  }

  createComment(comment: Comment): Observable<Comment> {
    return this.http.post<Comment>(this.rootUrl + this.apiContext + `?_csrf=${this.auth.csrfCookie}`, comment, this.postHttpOptions)
      .pipe(
        catchError(this.errors.handleError('createComment', new Comment()))
      );
  }

}
