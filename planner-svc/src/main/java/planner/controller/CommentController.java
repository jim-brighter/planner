package planner.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import planner.domain.jpa.Comment;
import planner.service.CommentService;

@RestController
@RequestMapping(value = "/api/comments")
public class CommentController {

	@Inject
	private CommentService commentService;
	
	private static final String APPLICATION_JSON = "application/json";
	
	@ApiOperation(value = "Create a comment")
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
	public Comment createComment(@RequestBody Comment comment) {
		return commentService.createComment(comment);
	}
	
	@ApiOperation(value = "Find all comments")
	@RequestMapping(value = "", method = RequestMethod.GET, produces = APPLICATION_JSON)
	public List<Comment> retrieveComments() {
		return commentService.findAllComments();
	}
	
	@ApiOperation(value = "Update comments")
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
	public List<Comment> updateComments(@RequestBody List<Comment> comments) {
		return commentService.updateComments(comments);
	}
	
	@ApiOperation(value = "Delete comments")
	@RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = APPLICATION_JSON)
	public void deleteComments(@RequestBody List<Comment> comments) {
		commentService.deleteComments(comments);
	}
}
