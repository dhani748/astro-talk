package com.astrotalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main entry point for the AstroTalk application. Bootstraps the Spring Boot application
 * with scheduling support enabled.
 */
@SpringBootApplication
@EnableScheduling
public class AstroTalkApplication {

    /**
     * Launches the Spring Boot application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AstroTalkApplication.class, args);
    }
}
