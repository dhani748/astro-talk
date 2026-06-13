package com.astrotalk.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "openapi")
public class OpenApiProperties {

    private String title = "AstroTalk API";

    private String version = "1.0.0";

    private String description = "AstroTalk Astrology Consultation Platform API";

    private Contact contact = new Contact();

    private License license = new License();

    private String serverUrl = "http://localhost:8080";

    @Getter
    @Setter
    public static class Contact {

        private String name = "AstroTalk";

        private String email = "support@astrotalk.com";

        private String url = "https://www.astrotalk.com";
    }

    @Getter
    @Setter
    public static class License {

        private String name = "AstroTalk License";

        private String url = "https://astrotalk.com/privacy-policy";
    }
}
