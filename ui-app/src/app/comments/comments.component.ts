import { Component, OnInit } from '@angular/core';

import { CommentService } from '../comment.service';
import { Comment } from '../comment';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {

  comments: Comment[];

  newComment: Comment = new Comment();

  constructor(private commentService: CommentService) { }

  ngOnInit() {
    this.retrieveComments();
  }

  retrieveComments(): void {
    this.commentService.getComments().subscribe(data => {
      this.comments = data;
    });
  }

  saveNewComment(): void {
    this.commentService.createComment(this.newComment).subscribe(data => {
      this.comments.push(data);
      this.newComment.clear();
    });
  }

}
