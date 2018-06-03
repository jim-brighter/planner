import { Component, OnInit, Input } from '@angular/core';

const listMap: { [id: string] : String } = {
  "TO_DO": "Done",
  "TO_EAT": "Eaten",
  "TO_COOK": "Cooked"
};

@Component({
  selector: 'app-lists',
  templateUrl: './lists.component.html',
  styleUrls: ['./lists.component.css']
})
export class ListsComponent implements OnInit {

  listTitle: String;
  completedListTitle: String;

  @Input() list: string;

  constructor() { }

  ngOnInit() {
    this.setListToShow();
  }

  setListToShow(): void {
    this.listTitle = this.makePresentable(this.list);
    this.completedListTitle = listMap[this.list];
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

}
