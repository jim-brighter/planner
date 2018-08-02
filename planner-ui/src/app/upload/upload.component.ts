import { Component, OnInit } from '@angular/core';
import { FileUploader } from 'ng2-file-upload';
import { ImageService } from '../image.service';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent implements OnInit {

  public uploader: FileUploader = new FileUploader({});

  constructor(private imageService: ImageService) { }

  ngOnInit() {
    this.uploader.options.isHTML5 = true;
  }

  upload() {
    let queueLength: number = this.uploader.queue.length;
    let formData = new FormData();

    if (queueLength > 0) {
      for (let i = 0; i < queueLength; i++) {
        formData.append('images', this.uploader.queue[i].file.rawFile);
      }

      this.imageService.uploadImages(formData).subscribe(data => {
        console.log("upload success");
      })
    } else {
      console.log("no images selected");
    }
  }

}
