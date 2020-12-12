package auth.planner.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@Profile("prod")
public class ProdCorsConfig {

    private static final Logger logger = LoggerFactory.getLogger(ProdCorsConfig.class);

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        logger.info("Loading prod CORS config");
        List<String> origins = new ArrayList<String>();
        origins.add("https://jimandfangzhuo.com");
        List<String> methods = new ArrayList<String>();
        methods.add("GET");
        methods.add("POST");
        methods.add("OPTIONS");
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(origins);
        config.setAllowedMethods(methods);
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
