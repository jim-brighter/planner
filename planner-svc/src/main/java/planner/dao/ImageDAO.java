package planner.dao;

import java.util.List;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import planner.domain.jpa.Event;
import planner.domain.jpa.Image;

public interface ImageDAO extends JpaRepository<Image, Long> {

    public List<Image> findByParentEvent(Event event);

    @Modifying
    @Query("update Image img set img.rotation = :rotation where img.id = :id")
    public void updateRotation(@Param(value = "id") long id, @Param(value = "rotation") int rotation);

}
