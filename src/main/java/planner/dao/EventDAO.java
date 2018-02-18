package planner.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import planner.jpa.Event;

public interface EventDAO extends JpaRepository<Event, Long> {

	public Event findById(long id);
}
