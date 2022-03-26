package planner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import planner.dao.ImageDAO;
import planner.domain.dto.ImageRotationRequest;
import planner.domain.jpa.Event;
import planner.domain.jpa.Image;
import planner.service.DigitalOceanService;
import planner.service.ImageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private DigitalOceanService doService;

    @Autowired
    private ImageDAO imageDAO;

    @Override
    public List<Image> saveImages(MultipartFile[] files) {
        return saveImages(files, null);
    }

    @Override
    @Transactional
    public List<Image> saveImages(MultipartFile[] files, Event event) {
        Map<String, Object> metaData = new HashMap<String, Object>();
        List<Image> images = new ArrayList<Image>();
        for (MultipartFile file : files) {
            try {
                metaData.put(HttpHeaders.CONTENT_LENGTH, file.getSize());
                metaData.put(HttpHeaders.CONTENT_TYPE, file.getContentType());
                String key = doService.store(file.getInputStream(), metaData);
                Image i = persistImageRecord(key, event);
                images.add(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return images;
    }

    private Image persistImageRecord(String key, Event event) {
        Image image = new Image();
        image.setDigitalOceanSpaceKey(key);
        image.setParentEvent(event);
        return imageDAO.save(image);
    }

    @Override
    public List<Image> getAllImages() {
        return imageDAO.findAll();
    }

    @Override
    public List<Image> getAllImagesForEvent(Event event) {
        // TODO: get all images FROM DIGITALOCEAN
        return imageDAO.findByParentEvent(event);
    }

    @Override
    @Transactional
    public void deleteImages(List<Image> images) {
        // TODO: delete all images FROM DIGITALOCEAN
        imageDAO.deleteAllInBatch(images);
    }

    @Override
    @Transactional
    public void updateRotation(ImageRotationRequest imageRotationRequest) {
        imageDAO.updateRotation(imageRotationRequest.getImageId(), imageRotationRequest.getRotation());
    }
}
