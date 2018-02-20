package planner.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import planner.dao.EventDAO;
import planner.domain.enums.EventStatus;
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
	@Transactional
	public void deleteEvents(List<Event> events) {
		logger.info("Deleting events {}", getIds(events));
		eventDAO.delete(events);
	}

	@Override
	@Transactional
	public List<Event> updateEvents(List<Event> events) {
		logger.info("Updating events {}", getIds(events));
		return eventDAO.save(events);
	}

	private List<Long> getIds(List<Event> events) {
		List<Long> ids = new ArrayList<Long>();
		for (Event e : events) {
			ids.add(e.getId());
		}
		return ids;
	}

}
