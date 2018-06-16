package planner.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import planner.domain.jpa.Comment;

public interface CommentDAO extends JpaRepository<Comment, Long> {

}
