package planner.domain.dto;

import java.io.Serializable;

public class ImageRotationRequest implements Serializable {

    private long imageId;
    private int rotation;

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
}
