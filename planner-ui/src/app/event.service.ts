import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap, timeout } from 'rxjs/operators';

import { environment } from '../environments/environment';
import { PlannerEvent } from './event';
import { AuthenticationService } from './authentication.service';
import { ErrorService } from './error.service';

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

  constructor(private http: HttpClient, private auth: AuthenticationService, private errors: ErrorService) { }

  getEvents(): Observable<PlannerEvent[]> {
    return this.http.get<PlannerEvent[]>(this.rootUrl + this.apiContext, this.getHttpOptions)
      .pipe(
        catchError(this.errors.handleError('getEvents', new Array<PlannerEvent>()))
      );
  }

  getEventsByType(type): Observable<PlannerEvent[]> {
    return this.http.get<PlannerEvent[]>(this.rootUrl + this.apiContext + `/type/${type}`, this.getHttpOptions)
      .pipe(
        catchError(this.errors.handleError('getEventsByType', new Array<PlannerEvent>()))
      );
  }

  saveEvent(event: PlannerEvent): Observable<PlannerEvent> {
    return this.http.post<PlannerEvent>(this.rootUrl + this.apiContext + `?_csrf=${this.auth.csrfCookie}`, event, this.postHttpOptions)
      .pipe(
        catchError(this.errors.handleError('saveEvent', new PlannerEvent()))
      );
  }

  deleteEvent(events: PlannerEvent[]): Observable<PlannerEvent> {
    return this.http.post<PlannerEvent[]>(this.rootUrl + this.apiContext + `/delete?_csrf=${this.auth.csrfCookie}`,
      events, this.postHttpOptions)
      .pipe(
        catchError(this.errors.handleError('deleteEvent', null))
      );
  }

  updateEvents(events: PlannerEvent[]): Observable<PlannerEvent[]> {
    return this.http.post<PlannerEvent[]>(this.rootUrl + this.apiContext + `/update?_csrf=${this.auth.csrfCookie}`,
      events, this.postHttpOptions)
      .pipe(
        catchError(this.errors.handleError('updateEvents', new Array<PlannerEvent>()))
      );
  }

}
