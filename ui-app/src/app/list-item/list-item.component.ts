import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { PlannerEvent } from '../event';

@Component({
  selector: 'app-list-item',
  templateUrl: './list-item.component.html',
  styleUrls: ['./list-item.component.css']
})
export class ListItemComponent implements OnInit {

  @Input() plannerEvent: PlannerEvent;

  @Output() itemToDelete = new EventEmitter<PlannerEvent>();
  @Output() itemToComplete = new EventEmitter<PlannerEvent>();

  showButtons: boolean = false;

  constructor() { }

  ngOnInit() {
  }

  deleteItem(): void {
    this.itemToDelete.emit(this.plannerEvent);
  }

  completeItem(): void {
    this.itemToComplete.emit(this.plannerEvent);
  }

}
