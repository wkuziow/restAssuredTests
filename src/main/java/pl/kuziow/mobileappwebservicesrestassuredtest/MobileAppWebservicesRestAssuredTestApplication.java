package pl.kuziow.mobileappwebservicesrestassuredtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MobileAppWebservicesRestAssuredTestApplication extends SpringBootServletInitializer {


    public static void main(String[] args) {
        SpringApplication.run(MobileAppWebservicesRestAssuredTestApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MobileAppWebservicesRestAssuredTestApplication.class);
    }
}
