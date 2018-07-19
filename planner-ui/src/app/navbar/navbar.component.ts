import { Component, OnInit, Input } from '@angular/core';

import { EventService } from '../event.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  results;

  @Input() linkColor: string;

  constructor(private eventService: EventService) { }

  ngOnInit() {
  }

}
