package com.farm.environment.configuration;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class FarmApplication {

    public static void main(String[] args) {
        //SpringApplication.run(FarmApplication.class);
        new SpringApplicationBuilder()
                .bannerMode(Banner.Mode.OFF)
                .sources(
                        BeansConfiguration.class,
                        DatabaseConfiguration.class,
                        FarmPropertySource.class)
                .addCommandLineProperties(true)
                .run(args);
    }
}
