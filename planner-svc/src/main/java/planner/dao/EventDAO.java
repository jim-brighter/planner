package planner.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import planner.domain.enums.EventStatus;
import planner.domain.enums.EventType;
import planner.domain.jpa.Event;

public interface EventDAO extends JpaRepository<Event, Long> {

	/**
	 * Find events by type that are not deleted
	 * 
	 * @param eventType
	 * @param eventStatus
	 * @return
	 */
	public List<Event> findByEventTypeAndEventStatusNotOrderById(EventType eventType, EventStatus eventStatus);
}
