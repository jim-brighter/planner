package planner.service;

import java.util.List;

import planner.domain.jpa.Comment;

public interface CommentService {

	public Comment createComment(Comment comment);
	
	public List<Comment> findAllComments();
	
	public void deleteComments(List<Comment> comments);
	
	public List<Comment> updateComments(List<Comment> comments);
}
