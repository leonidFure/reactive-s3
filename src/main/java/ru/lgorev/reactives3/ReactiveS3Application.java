package ru.lgorev.reactives3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ReactiveS3Application {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveS3Application.class, args);
    }

}
