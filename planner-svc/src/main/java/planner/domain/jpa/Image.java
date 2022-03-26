package planner.domain.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "IMAGES")
public class Image implements Serializable {

    private static final long serialVersionUID = -8183662133829448118L;

    @Id
    @SequenceGenerator(name = "image_gen", sequenceName = "IMAGE_SEQ", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_gen")
    @Column(name = "IMAGE_ID")
    private long id;

    @Column(name = "DIGITAL_OCEAN_KEY")
    @NotNull
    private String digitalOceanSpaceKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_event")
    private Event parentEvent;

    @Column(name = "ROTATION")
    private int rotation;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDigitalOceanSpaceKey() {
        return digitalOceanSpaceKey;
    }

    public void setDigitalOceanSpaceKey(String digitalOceanSpaceKey) {
        this.digitalOceanSpaceKey = digitalOceanSpaceKey;
    }

    public Event getParentEvent() {
        return parentEvent;
    }

    public void setParentEvent(Event parentEvent) {
        this.parentEvent = parentEvent;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
}
