import { Component, OnInit, OnDestroy } from '@angular/core';
import { PlannerImage } from '../image';
import { ImageService } from '../image.service';
import { ErrorService } from '../error.service';
import { AuthenticationService } from '../authentication.service';
import { Router, NavigationEnd } from '@angular/router';
import { faSignOutAlt, faRotate } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-photos',
  templateUrl: './photos.component.html',
  styleUrls: ['./photos.component.css']
})
export class PhotosComponent implements OnInit, OnDestroy {

  navigationSubscription;

  isLoading = true;

  digitalOceanSpace = 'https://image-space-jbrighter92.nyc3.digitaloceanspaces.com/';
  selectedImage: PlannerImage;

  faSignOutAlt = faSignOutAlt;
  faRotate = faRotate;

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
    if (this.authenticated()) {
      this.getImages();
    }
  }

  authenticated(): boolean {
    return this.authenticator.authenticated;
  }

  getImages(): void {
    this.imageService.getAllImages().subscribe(data => {
      this.images = data;
      this.isLoading = false;
    });
  }

  zoomImage(image): void {
    this.selectedImage = image;
  }

  rotate(image): void {
    const degrees = parseInt(prompt("How many degrees to rotate?"));
    if (degrees === undefined || isNaN(degrees)) {
      return;
    }
    if (![0, 90, 180, 270, 360].includes(degrees)) {
      alert("Invalid rotation amount, please use 0, 90, 180, or 270 degrees");
      return;
    }
    this.imageService.rotateImage(image.id, degrees).subscribe(data => {
      window.location.reload();
    });
  }

  ngOnDestroy() {
    if (this.navigationSubscription) {
      this.navigationSubscription.unsubscribe();
    }
  }

}
