package planner.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import planner.domain.jpa.Event;
import planner.domain.jpa.Image;

public interface ImageService {

	public List<Image> saveImages(MultipartFile[] files);
	
	public List<Image> saveImages(MultipartFile[] files, Event event);
	
	public List<Image> getAllImages();
	
	public List<Image> getAllImagesForEvent(Event event);
	
	public void deleteImages(List<Image> images);
}
