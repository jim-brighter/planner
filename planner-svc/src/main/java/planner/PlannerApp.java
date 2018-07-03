package planner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@SpringBootApplication
@EnableTransactionManagement
public class PlannerApp {
	
	@Value("${aws.access.key}")
	private String ACCESS_KEY;
	
	@Value("${aws.secret.key}")
	private String SECRET_KEY;
	
	public PlannerApp() {
		
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(PlannerApp.class, args);
	}
	
	@Bean
	public AmazonS3 digitalOceanClient() {
		return AmazonS3ClientBuilder.standard()
				.withEndpointConfiguration(
						new AwsClientBuilder.EndpointConfiguration("nyc3.digitaloceanspaces.com", "nyc3")
				)
				.withCredentials(
						new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY))
				)
				.build();
	}

}
