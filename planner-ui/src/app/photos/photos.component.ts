import { Component, OnInit } from '@angular/core';
import { PlannerImage } from '../image';
import { ImageService } from '../image.service';

@Component({
  selector: 'app-photos',
  templateUrl: './photos.component.html',
  styleUrls: ['./photos.component.css']
})
export class PhotosComponent implements OnInit {

  constructor(private imageService: ImageService) { }

  images: PlannerImage[];

  ngOnInit() {
    this.getImages();
  }

  getImages(): void {
    this.imageService.getAllImages().subscribe(data => {
      this.images = data;
    });
  }

}
