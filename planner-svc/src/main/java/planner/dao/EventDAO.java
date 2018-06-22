package planner.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import planner.domain.enums.EventType;
import planner.domain.jpa.Event;

public interface EventDAO extends JpaRepository<Event, Long> {

	public Event findById(long id);
	
	public List<Event> findByEventType(EventType eventType);
}
