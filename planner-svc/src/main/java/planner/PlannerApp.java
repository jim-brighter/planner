package planner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;
import java.net.URISyntaxException;

import static planner.constant.Constants.SPACE_NAME;

@SpringBootApplication
@EnableTransactionManagement
public class PlannerApp {

    public PlannerApp() {}

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        SpringApplication.run(PlannerApp.class, args);
    }

    @Bean
    public S3Client digitalOceanClient() throws URISyntaxException {
        return S3Client.builder().endpointOverride(new URI("http://nyc3.digitaloceanspaces.com")).region(Region.of("nyc3")).build();
    }

}
