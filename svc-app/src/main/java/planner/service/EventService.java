package planner.service;

import java.util.List;

import planner.domain.enums.EventType;
import planner.domain.jpa.Event;

public interface EventService {
	
	public Event createEvent(Event event);
	
	public Event findEvent(long id);
	
	public List<Event> findAllEvents();
	
	public List<Event> findEvents(EventType eventType);
	
	public void deleteEvents(List<Event> eventIDs);
	
	public List<Event> updateEvents(List<Event> events);
		
}
