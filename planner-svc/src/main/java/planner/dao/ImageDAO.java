package planner.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import planner.domain.jpa.Event;
import planner.domain.jpa.Image;

public interface ImageDAO extends JpaRepository<Image, Long> {

    public List<Image> findByParentEvent(Event event);

}
