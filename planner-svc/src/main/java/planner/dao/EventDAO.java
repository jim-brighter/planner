package planner.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import planner.domain.enums.EventStatus;
import planner.domain.enums.EventType;
import planner.domain.jpa.Event;

public interface EventDAO extends JpaRepository<Event, Long> {

	public Event findById(long id);
	
	/**
	 * Find events by type that are not deleted
	 * 
	 * @param eventType
	 * @param eventStatus
	 * @return
	 */
	public List<Event> findByEventTypeAndEventStatusNot(EventType eventType, EventStatus eventStatus);
}
