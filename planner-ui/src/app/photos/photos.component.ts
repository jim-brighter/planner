import { Component, OnInit } from '@angular/core';
import { PlannerImage } from '../image';
import { ImageService } from '../image.service';
import { ErrorService } from '../error.service';
import { AuthenticationService } from '../authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-photos',
  templateUrl: './photos.component.html',
  styleUrls: ['./photos.component.css']
})
export class PhotosComponent implements OnInit {

  constructor(private imageService: ImageService, 
    public errors: ErrorService, 
    private authenticator: AuthenticationService,
    private router: Router) { }

  images: PlannerImage[];

  ngOnInit() {
    if (this.authenticated()) {
      this.getImages();
    }
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

}
