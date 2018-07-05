package planner.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.Headers;

import planner.dao.ImageDAO;
import planner.domain.jpa.Event;
import planner.domain.jpa.Image;
import planner.service.DigitalOceanService;
import planner.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

	@Inject
	private DigitalOceanService doService;
	
	@Inject
	private ImageDAO imageDAO;
	
	@Override
	public List<Long> saveImages(MultipartFile[] files) {
		return saveImages(files, null);
	}

	@Override
	@Transactional
	public List<Long> saveImages(MultipartFile[] files, Event event) {
		Map<String, Object> metaData = new HashMap<String, Object>();
		List<Long> ids = new ArrayList<Long>();
		for (MultipartFile file : files) {
			try {
				metaData.put(Headers.CONTENT_LENGTH, file.getSize());
				String key = doService.store(file.getInputStream(), metaData);
				long id = persistImageRecord(key, event);
				ids.add(id);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ids;
	}
	
	private long persistImageRecord(String key, Event event) {
		Image image = new Image();
		image.setDigitalOceanSpaceKey(key);
		image.setParentEvent(event);
		return imageDAO.save(image).getId();
	}

	@Override
	public List<Image> getAllImages() {
		//TODO: get all images FROM DIGITALOCEAN
		return imageDAO.findAll();
	}

	@Override
	public List<Image> getAllImagesForEvent(Event event) {
		//TODO: get all images FROM DIGITALOCEAN
		return imageDAO.findByParentEvent(event);
	}

	@Override
	@Transactional
	public void deleteImages(List<Image> images) {
		//TODO: delete all images FROM DIGITALOCEAN
		imageDAO.delete(images);
	}

}
