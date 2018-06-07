import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap, timeout } from 'rxjs/operators';

import { environment } from '../environments/environment';
import { PlannerEvent } from './event'

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
}

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private rootUrl = environment.plannerBackendRootUrl;
  private apiContext = environment.plannerBackendEventsContext;

  constructor(private http: HttpClient) { }

  getEvents(): Observable<PlannerEvent[]> {
    return this.http.get<PlannerEvent[]>(this.rootUrl + this.apiContext)
      .pipe(
        catchError(this.handleError('getEvents'))
      );
  }

  getEventsByType(type): Observable<PlannerEvent[]> {
    return this.http.get<PlannerEvent[]>(this.rootUrl + this.apiContext + `/type/${type}`)
      .pipe(
        catchError(this.handleError('getEventsByType'))
      );
  }

  saveEvent(event: PlannerEvent): Observable<PlannerEvent> {
    return this.http.post<PlannerEvent>(this.rootUrl + this.apiContext, event, httpOptions)
      .pipe(
        catchError(this.handleError('saveEvent'))
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
