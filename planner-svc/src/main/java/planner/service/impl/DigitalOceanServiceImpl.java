package planner.service.impl;

import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

import planner.service.DigitalOceanService;

@Service
public class DigitalOceanServiceImpl implements DigitalOceanService {
	
	@Inject 
	AmazonS3 doClient;
	
	private static final String SPACE_NAME = "image-space-jbrighter92";

	@Override
	public S3Object retrieve(String key) {
		return doClient.getObject(SPACE_NAME, key);
	}

	@Override
	public void delete(String key) {
		doClient.deleteObject(SPACE_NAME, key);
	}

	@Override
	public String store(InputStream is, Map<String, Object> metaData) {
		String key = UUID.randomUUID().toString();
		doClient.putObject(SPACE_NAME, key, is, buildMetadata(metaData));
		return key;
	}
	
	private ObjectMetadata buildMetadata(Map<String, Object> metaData) {
		ObjectMetadata objectMetadata = new ObjectMetadata();
		if (!CollectionUtils.isEmpty(metaData)) {
			objectMetadata.setContentLength((long) metaData.get(Headers.CONTENT_LENGTH));
			objectMetadata.setHeader("x-amz-acl", "public-read");
		}
		return objectMetadata;
	}

}
