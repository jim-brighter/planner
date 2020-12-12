package auth.planner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PlannerAuthApp {

    public PlannerAuthApp() {

    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(PlannerAuthApp.class, args);
    }
}
