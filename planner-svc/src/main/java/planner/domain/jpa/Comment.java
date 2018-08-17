package planner.domain.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "COMMENTS")
public class Comment implements Serializable {

	private static final long serialVersionUID = -5008944148287360156L;
	
	@Id
	@SequenceGenerator(name = "comment_gen", sequenceName = "COMMENT_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_gen")
	@Column(name = "COMMENT_ID")
	private long id;
	
	@Column(name = "COMMENT_TEXT")
	@NotNull
	private String commentText;
	
	@Column(name = "COMMENT_CREATED_TIME")
	private Date createdTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commentText == null) ? 0 : commentText.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (commentText == null) {
			if (other.commentText != null)
				return false;
		} else if (!commentText.equals(other.commentText))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
}
