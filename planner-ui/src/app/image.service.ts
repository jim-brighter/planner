import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap, timeout } from 'rxjs/operators';

import { environment } from '../environments/environment';
import { PlannerImage } from './image';
import { pipe } from '@angular/core/src/render3/pipe';
import { AuthenticationService } from './authentication.service';
import { ErrorService } from './error.service';

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  private rootUrl = environment.plannerBackendRootUrl;
  private apiContext = environment.plannerBackendImageContext;

  private postHttpOptions = {
    headers: new HttpHeaders({
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

  constructor(private http: HttpClient, private auth: AuthenticationService, private errors: ErrorService) { }

  uploadImages(images: FormData): Observable<PlannerImage[]> {
    return this.http.post<FormData>(this.rootUrl + this.apiContext + `?_csrf=${this.auth.csrfCookie}`, images, this.postHttpOptions)
      .pipe(
        catchError(this.handleError('uploadImages', null))
      );
  }

  getAllImages(): Observable<PlannerImage[]> {
    return this.http.get<PlannerImage[]>(this.rootUrl + this.apiContext, this.getHttpOptions)
      .pipe(
        catchError(this.handleError('getAllImages', null))
      );
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      this.errors.addError(`${operation} failed! Show Jim this error!`);;
      return of(result as T);
    }
  }
}
