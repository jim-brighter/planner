import { Component, OnInit, Input } from '@angular/core';

const TO_DO: String = 'TO_DO';
const TO_EAT: String = 'TO_EAT';
const TO_COOK: String = 'TO_COOK';

@Component({
  selector: 'app-lists',
  templateUrl: './lists.component.html',
  styleUrls: ['./lists.component.css']
})
export class ListsComponent implements OnInit {

  showDo: boolean;
  showEat: boolean;
  showCook: boolean;

  @Input() list: String;

  constructor() { }

  ngOnInit() {
    this.setListToShow();
  }

  setListToShow(): void {
    this.showDo = this.list === TO_DO;
    this.showEat = this.list === TO_EAT;
    this.showCook = this.list === TO_COOK;
  }

}
