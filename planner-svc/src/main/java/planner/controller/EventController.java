package planner.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import planner.domain.enums.EventType;
import planner.domain.jpa.Event;
import planner.service.EventService;

@RestController
@RequestMapping(value = "/api/events")
public class EventController {

    @Inject
    private EventService eventService;

    private static final String APPLICATION_JSON = "application/json";

    @ApiOperation(value = "Create an event")
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @ApiOperation(value = "Find all events")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public List<Event> findAllEvents() {
        return eventService.findAllEvents();
    }

    @ApiOperation(value = "Find events by type")
    @RequestMapping(value = "/type/{eventType}", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public List<Event> findAllEventsByType(@PathVariable(value = "eventType") String eventType) {
        return eventService.findEvents(EventType.valueOf(eventType));
    }

    @ApiOperation(value = "Delete events")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = APPLICATION_JSON)
    public void deleteEvents(@RequestBody List<Event> events) {
        eventService.deleteEvents(events);
    }

    @ApiOperation(value = "Update events")
    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public List<Event> updateEvents(@RequestBody List<Event> events) {
        return eventService.updateEvents(events);
    }

}
