import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms'; // <-- NgModel lives here

import { AppComponent } from './app.component';
import { CommentsComponent } from './comments/comments.component';
import { ListsComponent } from './lists/lists.component';
import { DetailsComponent } from './details/details.component';
import { AppRoutingModule } from './/app-routing.module';
import { HomeComponent } from './home/home.component';
import { NavbarComponent } from './navbar/navbar.component';
import { EventService } from './event.service';
import { ListItemComponent } from './list-item/list-item.component';
import { CommentService } from './comment.service';
import { CommentItemComponent } from './comment-item/comment-item.component';
import { UploadComponent } from './upload/upload.component';
import { FileUploadModule } from 'ng2-file-upload';
import { ImageService } from './image.service';

@NgModule({
  declarations: [
    AppComponent,
    CommentsComponent,
    ListsComponent,
    DetailsComponent,
    HomeComponent,
    NavbarComponent,
    ListItemComponent,
    CommentItemComponent,
    UploadComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    FileUploadModule
  ],
  providers: [
    EventService,
    CommentService,
    ImageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
