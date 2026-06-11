package com.astrotalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AstroTalkApplication {

    public static void main(String[] args) {
        SpringApplication.run(AstroTalkApplication.class, args);
    }
}
