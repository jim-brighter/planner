package planner.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import planner.dao.CommentDAO;
import planner.domain.jpa.Comment;
import planner.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
	
	@Inject
	private CommentDAO commentDAO;

	@Override
	@Transactional
	public Comment createComment(Comment comment) {
		logger.info("Creating new comment '{}'", comment.getCommentText());
		comment.setCreatedTime(new Date(System.currentTimeMillis()));
		return commentDAO.save(comment);
	}

	@Override
	public List<Comment> findAllComments() {
		logger.info("Finding all comments");
		return commentDAO.findAll();
	}

	@Override
	@Transactional
	public void deleteComments(List<Comment> comments) {
		logger.info("Deleting comments {}", getIdsAndText(comments));
		commentDAO.deleteInBatch(comments);
	}

	@Override
	@Transactional
	public List<Comment> updateComments(List<Comment> comments) {
		logger.info("Updating comments {}", getIdsAndText(comments));
		return commentDAO.saveAll(comments);
	}
	
	private List<String> getIdsAndText(List<Comment> comments) {
		List<String> ids = new ArrayList<String>();
		for (Comment c : comments) {
			ids.add("[" + c.getId() + ": " + c.getCommentText() + "]");
		}
		return ids;
	}

}
