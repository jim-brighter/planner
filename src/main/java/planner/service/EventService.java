package planner.service;

import java.util.List;

import planner.jpa.Event;

public interface EventService {
	
	public Event createEvent(Event event);
	
	//TODO: change this to use a DTO
	public Event createEvent(String title, String description, String eventType);
	
	public Event findEvent(long id);
	
	public List<Event> findAllEvents();
	
	public void deleteEvents(List<Event> events);
	
	//TODO: change this to use a DTO	
	public List<Event> updateEvents(List<Event> events);

}
