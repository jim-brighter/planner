import { Component, OnInit } from '@angular/core';

import { EventService } from '../event.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  results;

  constructor(private eventService: EventService) { }

  ngOnInit() {
  }

  testClick() {
    this.eventService.getEvents().subscribe(events => this.results = events);
  }

}
