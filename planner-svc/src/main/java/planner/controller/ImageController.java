package planner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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

}
