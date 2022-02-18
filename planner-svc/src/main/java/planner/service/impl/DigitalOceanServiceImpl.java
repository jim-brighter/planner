package planner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import planner.service.DigitalOceanService;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

import static planner.constant.Constants.SPACE_NAME;

@Service
public class DigitalOceanServiceImpl implements DigitalOceanService {

    public static final String X_AMZ_ACL = "x-amz-acl";
    public static final String PUBLIC_READ = "public-read";

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
                .metadata(Map.of(
                        X_AMZ_ACL, PUBLIC_READ,
                        HttpHeaders.CONTENT_TYPE, (String) metaData.get(HttpHeaders.CONTENT_TYPE),
                        HttpHeaders.CONTENT_LENGTH, (String) metaData.get(HttpHeaders.CONTENT_LENGTH)
                ))
                .build();
        doClient.putObject(putObjectRequest, RequestBody.fromInputStream(is, (long) metaData.get(HttpHeaders.CONTENT_LENGTH)));
        return key;
    }
}
