import { Component, OnInit, Input } from '@angular/core';

import { EventService } from '../event.service';
import { PlannerEvent } from '../event';

const listMap: { [id: string] : String } = {
  "TO_DO": "Done",
  "TO_EAT": "Eaten",
  "TO_COOK": "Cooked"
};

const TO_DO = "TO_DO";
const COMPLETE = "COMPLETE";

@Component({
  selector: 'app-lists',
  templateUrl: './lists.component.html',
  styleUrls: ['./lists.component.css']
})
export class ListsComponent implements OnInit {

  listTitle: String;
  completedListTitle: String;

  listData: PlannerEvent[];
  toDoEvents: PlannerEvent[];
  completeEvents: PlannerEvent[];

  newEvent: PlannerEvent = new PlannerEvent();

  @Input() list: string;

  constructor(private eventService: EventService) { }

  ngOnInit() {
    this.setListToShow();
    this.populateLists();
  }

  setListToShow(): void {
    this.listTitle = this.makePresentable(this.list);
    this.completedListTitle = listMap[this.list];
  }

  populateLists(): void {
    this.eventService.getEventsByType(this.list).subscribe(data => {
      this.listData = data;
      this.toDoEvents = this.listData.filter(event => event.eventStatus === TO_DO);
      this.completeEvents = this.listData.filter(event => event.eventStatus === COMPLETE);
    });
  }

  makePresentable(s): String {
    s = s.toLowerCase().replace("_", " ");
    for (let i = 0; i < s.length; i++) {
      if (i === 0 || s.charAt(i - 1) === " ") {
        s = s.substring(0, i) + s.charAt(i).toUpperCase() + s.substring(i + 1);
      }
    }
    return s;
  }

  saveNewEvent(): void {
    this.newEvent.eventType = this.list;
    this.eventService.saveEvent(this.newEvent).subscribe(data => {
      this.toDoEvents.push(data);
      this.newEvent.clear();
    });
  }

  onDelete(toDelete: PlannerEvent): void {
    let toDeleteArray: PlannerEvent[] = [toDelete];
    this.eventService.deleteEvent(toDeleteArray).subscribe(() => {
      this.populateLists();
    });
  }

  onComplete(toComplete: PlannerEvent): void {
    toComplete.eventStatus = COMPLETE;
    let toCompleteArray = [toComplete];
    this.eventService.updateEvents(toCompleteArray).subscribe(() => {
      this.populateLists();
    })
  }

  ngOnChanges() {
    this.setListToShow();
    this.populateLists();
  }

}
