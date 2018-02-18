package planner.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import planner.jpa.Event;
import planner.service.EventService;

@RestController
@RequestMapping(value = "/api")
public class EventController {

	@Inject
	private EventService eventService;
	
	private final String applicationJson = "application/json";
	
	@ApiOperation(value = "Create an event")
	@RequestMapping(value = "/events", method = RequestMethod.POST, consumes = applicationJson, produces = applicationJson)
	public Event createEvent(@RequestBody Event event) {
		return eventService.createEvent(event);
	}
	
	@ApiOperation(value = "Find an event by ID")
	@RequestMapping(value = "/events/{id}", method = RequestMethod.GET, produces = applicationJson)
	public Event findById(@PathVariable(value = "id") String id) {
		return eventService.findEvent(Long.parseLong(id));
	}
	
	@ApiOperation(value = "Find all events")
	@RequestMapping(value = "/events", method = RequestMethod.GET, produces = applicationJson)
	public List<Event> findAllEvents() {
		return eventService.findAllEvents();
	}
	
	@ApiOperation(value = "Delete events")
	@RequestMapping(value = "/events/delete", method = RequestMethod.POST, consumes = applicationJson)
	public void deleteEvents(@RequestBody List<Event> events) {
		eventService.deleteEvents(events);
	}
	
	@ApiOperation(value = "Update events")
	@RequestMapping(value = "/events/update", method = RequestMethod.POST, consumes = applicationJson, produces = applicationJson)
	public List<Event> updateEvents(@RequestBody List<Event> events) {
		return eventService.updateEvents(events);
	}

}
