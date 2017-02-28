package com.farm;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class FarmApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(FarmApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }
}