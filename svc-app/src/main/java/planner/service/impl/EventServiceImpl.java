package planner.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import planner.dao.EventDAO;
import planner.domain.enums.EventStatus;
import planner.domain.enums.EventType;
import planner.domain.jpa.Event;
import planner.service.EventService;

@Service
public class EventServiceImpl implements EventService {
	
	private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

	@Inject
	private EventDAO eventDAO;

	@Override
	@Transactional
	public Event createEvent(Event event) {
		logger.info("Creating new event with title '{}'", event.getTitle());
		event.setEventStatus(EventStatus.TO_DO);
		event.setCreatedTime(new Date(System.currentTimeMillis()));
		return eventDAO.save(event);
	}

	@Override
	public Event findEvent(long id) {
		logger.info("Finding event with ID {}", id);
		return eventDAO.findById(id);
	}

	@Override
	public List<Event> findAllEvents() {
		logger.info("Finding all events");
		return eventDAO.findAll();
	}
	
	@Override
	public List<Event> findEvents(EventType eventType) {
		logger.info("Finding all events of type {}", eventType.toString());
		return eventDAO.findByEventType(eventType);
	}

	@Override
	@Transactional
	public void deleteEvents(List<Event> events) {
		logger.info("Deleting events {}", getIdsAndTitles(events));
		eventDAO.delete(events);
	}

	@Override
	@Transactional
	public List<Event> updateEvents(List<Event> events) {
		logger.info("Updating events {}", getIdsAndTitles(events));
		return eventDAO.save(events);
	}

	private List<String> getIdsAndTitles(List<Event> events) {
		List<String> ids = new ArrayList<String>();
		for (Event e : events) {
			ids.add("[" + e.getId() + ": " + e.getTitle() + "]");
		}
		return ids;
	}

}
