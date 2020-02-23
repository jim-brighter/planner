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
  @Output() itemToUpdate = new EventEmitter<PlannerEvent>();
  @Output() itemToEdit = new EventEmitter<PlannerEvent>();

  showButtons = false;
  updateAction = '';
  updateTitle = '';

  editing = false;

  constructor() { }

  ngOnInit() {
    this.updateAction = this.plannerEvent.eventStatus === 'TO_DO' ? '' : '';
    this.updateTitle = this.plannerEvent.eventStatus === 'TO_DO' ? 'Done' : 'Redo';
  }

  deleteItem(): void {
    this.itemToDelete.emit(this.plannerEvent);
  }

  updateItem(): void {
    this.itemToUpdate.emit(this.plannerEvent);
  }

  editItem(): void {
    this.editing = true;
  }

  saveEvent(): void {
    this.editing = false;
    this.itemToEdit.emit(this.plannerEvent);
  }

}
