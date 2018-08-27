import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap, timeout } from 'rxjs/operators';

import { environment } from '../environments/environment';
import { Comment } from './comment';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private rootUrl = environment.plannerBackendRootUrl;
  private apiContext = environment.plannerBackendCommentsContext;

  private postHttpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': this.auth.authToken
    }),
    withCredentials: true
  };

  private getHttpOptions = {
    headers: new HttpHeaders({
      'Authorization': this.auth.authToken
    }),
    withCredentials: true
  };

  constructor(private http: HttpClient, private auth: AuthenticationService) { }

  getComments(): Observable<Comment[]> {
    return this.http.get<Comment[]>(this.rootUrl + this.apiContext, this.getHttpOptions)
      .pipe(
        catchError(this.handleError('getComments', new Array<Comment>()))
      );
  }

  createComment(comment: Comment): Observable<Comment> {
    return this.http.post<Comment>(this.rootUrl + this.apiContext, comment, this.postHttpOptions)
      .pipe(
        catchError(this.handleError('createComment', new Comment()))
      );
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      alert(`${operation} failed - check the console for more information`);
      return of(result as T);
    };
  }
}
