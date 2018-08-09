import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap, timeout } from 'rxjs/operators';

import { environment } from '../environments/environment';
import { PlannerImage } from './image';
import { pipe } from '@angular/core/src/render3/pipe';

const uploadHttpOptions = {};

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  private rootUrl = environment.plannerBackendRootUrl;
  private apiContext = environment.plannerBackendImageContext;

  constructor(private http: HttpClient) { }

  uploadImages(images: FormData): Observable<PlannerImage[]> {
    return this.http.post<FormData>(this.rootUrl + this.apiContext, images, uploadHttpOptions)
      .pipe(
        catchError(this.handleError('uploadImages', null))
      );
  }

  getAllImages(): Observable<PlannerImage[]> {
    return this.http.get<PlannerImage[]>(this.rootUrl + this.apiContext)
      .pipe(
        catchError(this.handleError('getAllImages', null))
      );
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      alert(`${operation} failed - check the console for more information`);
      return of(result as T);
    }
  }
}
