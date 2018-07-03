package planner.service;

import java.io.InputStream;
import java.util.Map;

import com.amazonaws.services.s3.model.S3Object;

public interface DigitalOceanService {

	public S3Object retrieve(String key);
	
	public void delete(String key);
	
	public String store(InputStream is, Map<String, Object> metaData);
}
