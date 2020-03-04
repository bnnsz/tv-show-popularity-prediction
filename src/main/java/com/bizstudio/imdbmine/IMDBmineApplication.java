package com.bizstudio.imdbmine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class IMDBmineApplication {

    public static void main(String[] args) {
        SpringApplication.run(IMDBmineApplication.class, args);
    }

}
