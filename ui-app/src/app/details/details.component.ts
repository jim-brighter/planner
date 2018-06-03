import { Component, OnInit } from '@angular/core';

import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {

  list: String;

  constructor(private route: ActivatedRoute,
    private location: Location) { }

  ngOnInit() {
    this.getList()
  }

  getList(): void {
    const list = this.enumify(this.route.snapshot.paramMap.get('list'));
    this.list = list;
  }

  private enumify(listname): String {
    return listname.replace("-","_").toUpperCase();
  }

}
