import { Component, OnInit, OnDestroy } from '@angular/core';
import { PlannerImage } from '../image';
import { ImageService } from '../image.service';
import { ErrorService } from '../error.service';
import { AuthenticationService } from '../authentication.service';
import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-photos',
  templateUrl: './photos.component.html',
  styleUrls: ['./photos.component.css']
})
export class PhotosComponent implements OnInit, OnDestroy {

  navigationSubscription;

  constructor(private imageService: ImageService, 
    public errors: ErrorService, 
    private authenticator: AuthenticationService,
    private router: Router) { 

      this.navigationSubscription = this.router.events.subscribe((e: any) => {
        if (e instanceof NavigationEnd) {
          this.getImages();
        }
      });
    }

  images: PlannerImage[];

  ngOnInit() {
  }

  authenticated(): boolean {
    return this.authenticator.authenticated;
  }

  logout(): void {
    this.authenticator.logout();
    this.router.navigateByUrl('/');
  }

  getImages(): void {
    this.imageService.getAllImages().subscribe(data => {
      this.images = data;
    });
  }

  ngOnDestroy() {
    if (this.navigationSubscription) {
      this.navigationSubscription.unsubscribe();
    }
  }

}
