import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap, timeout } from 'rxjs/operators';

import { environment } from '../environments/environment';
import { PlannerEvent } from './event';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private rootUrl = environment.plannerBackendRootUrl;
  private apiContext = environment.plannerBackendEventsContext;

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

  getEvents(): Observable<PlannerEvent[]> {
    return this.http.get<PlannerEvent[]>(this.rootUrl + this.apiContext, this.getHttpOptions)
      .pipe(
        catchError(this.handleError('getEvents', new Array<PlannerEvent>()))
      );
  }

  getEventsByType(type): Observable<PlannerEvent[]> {
    return this.http.get<PlannerEvent[]>(this.rootUrl + this.apiContext + `/type/${type}`, this.getHttpOptions)
      .pipe(
        catchError(this.handleError('getEventsByType', new Array<PlannerEvent>()))
      );
  }

  saveEvent(event: PlannerEvent): Observable<PlannerEvent> {
    return this.http.post<PlannerEvent>(this.rootUrl + this.apiContext, event, this.postHttpOptions)
      .pipe(
        catchError(this.handleError('saveEvent', new PlannerEvent()))
      );
  }

  deleteEvent(events: PlannerEvent[]): Observable<PlannerEvent> {
    return this.http.post<PlannerEvent[]>(this.rootUrl + this.apiContext + '/delete', events, this.postHttpOptions)
      .pipe(
        catchError(this.handleError('deleteEvent', null))
      );
  }

  updateEvents(events: PlannerEvent[]): Observable<PlannerEvent[]> {
    return this.http.post<PlannerEvent[]>(this.rootUrl + this.apiContext + '/update', events, this.postHttpOptions)
      .pipe(
        catchError(this.handleError('updateEvents', new Array<PlannerEvent>()))
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
