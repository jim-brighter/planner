package planner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import planner.service.DigitalOceanService;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

import static planner.constant.Constants.SPACE_NAME;

@Service
public class DigitalOceanServiceImpl implements DigitalOceanService {

    @Autowired
    S3Client doClient;

    @Override
    public String retrieve(String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(SPACE_NAME)
                .key(key)
                .build();
        return doClient.getObject(getObjectRequest).response().toString();

    }

    @Override
    public void delete(String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(SPACE_NAME)
                .key(key)
                .build();
        doClient.deleteObject(deleteObjectRequest);
    }

    @Override
    public String store(InputStream is, Map<String, Object> metaData) {
        String contentType = (String) metaData.get(HttpHeaders.CONTENT_TYPE);
        String extension = contentType.substring(contentType.indexOf("/") + 1);
        String key = UUID.randomUUID().toString() + "." + extension;
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(SPACE_NAME)
                .key(key)
                .contentType((String) metaData.get(HttpHeaders.CONTENT_TYPE))
                .contentLength((long) metaData.get(HttpHeaders.CONTENT_LENGTH))
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();
        doClient.putObject(putObjectRequest, RequestBody.fromInputStream(is, (long) metaData.get(HttpHeaders.CONTENT_LENGTH)));
        return key;
    }
}
