package planner.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import planner.dao.EventDAO;
import planner.enums.EventType;
import planner.jpa.Event;
import planner.service.EventService;

@Service
public class EventServiceImpl implements EventService {
	
	private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

	@Inject
	private EventDAO eventDAO;
	
	@Override
	public Event createEvent(Event event) {
		logger.info("Saving event with title '{}'", event.getTitle());
		return eventDAO.save(event);
	}

	@Override
	public Event findEvent(long id) {
		logger.info("Finding event with id {}", id);
		return eventDAO.findById(id);
	}

	@Override
	public List<Event> findAllEvents() {
		logger.info("Finding all events");
		return eventDAO.findAll();
	}

	@Override
	public void deleteEvents(List<Event> events) {
		logger.info("Deleting events");
		eventDAO.deleteInBatch(events);
	}

	@Override
	public List<Event> updateEvents(List<Event> events) {
		
		//TODO: add a way to update status without posting the whole event
		
		logger.info("Updating events");
		return eventDAO.save(events);
	}

	@Override
	public Event createEvent(String title, String description, String eventType) {
		logger.info("Creating new event with title {}, description {}, of type {}", title, description, eventType);
		Event e = new Event();
		e.setTitle(title);
		e.setDescription(description);
		e.setEventType(EventType.valueOf(eventType));
		return eventDAO.save(e);
	}

}
