package planner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import planner.domain.dto.ImageRotationRequest;
import planner.domain.jpa.Event;
import planner.domain.jpa.Image;
import planner.service.ImageService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "multipart/form-data", produces = "application/json")
    // TODO: return a list of created IMAGES
    public List<Image> uploadImages(@RequestPart("images") MultipartFile[] files, @RequestPart("event") Event event)
            throws IOException {
        return imageService.saveImages(files);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public List<Image> getAllImages() {
        return imageService.getAllImages();
    }

    @RequestMapping(value = "/rotate", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void updateRotation(@RequestBody ImageRotationRequest imageRotationRequest) {
        imageService.updateRotation(imageRotationRequest);
    }

}
