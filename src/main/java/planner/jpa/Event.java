package planner.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import planner.enums.EventType;

@Entity
@Table(name = "EVENTS")
public class Event implements Serializable {

	private static final long serialVersionUID = 3776349549472636352L;

	@Id
	@SequenceGenerator(name = "event_gen", sequenceName = "EVENT_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_gen")
	@Column(name = "EVENT_ID")
	private long id;
	
	@Column(name = "EVENT_TITLE")
	private String title;
	
	@Column(name = "EVENT_DESCRIPTION")
	private String description;
	
	@Column(name = "EVENT_TYPE")
	@Enumerated(EnumType.STRING)
	private EventType eventType;
	
	@ElementCollection
	private List<String> eventComments;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public List<String> getEventComments() {
		return eventComments;
	}

	public void setEventComments(List<String> eventComments) {
		this.eventComments = eventComments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Event other = (Event) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (eventType != other.eventType)
			return false;
		if (id != other.id)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
}
