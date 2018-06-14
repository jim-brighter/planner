import { Component, OnInit } from '@angular/core';

import { ActivatedRoute, Router, NavigationEnd } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {

  list: String;

  navigationSubscription;

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private router: Router) {

      this.navigationSubscription = this.router.events.subscribe((e: any) => {
        if (e instanceof NavigationEnd) {
          this.getList()
        }
      });
    }

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

  ngOnDestroy() {
    if (this.navigationSubscription) {
      this.navigationSubscription.unsubscribe();
    }
  }

}
