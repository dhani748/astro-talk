package com.astrotalk.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = """
                JWT Bearer authentication using HTTP Authorization header.
                
                Format:
                Authorization: Bearer <JWT_TOKEN>
                
                This token is required for accessing secured APIs.
                """
)
@Slf4j
@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {

    private final OpenApiProperties properties;

    @Bean
    public OpenAPI openAPI() {
        log.info("Initializing Swagger OpenAPI configuration");

        OpenAPI openAPI = new OpenAPI()
                .info(buildInfo())
                .servers(List.of(buildServer()));

        log.info("Swagger OpenAPI initialized | title={} | version={} | server={}",
                properties.getTitle(),
                properties.getVersion(),
                properties.getServerUrl()
        );

        return openAPI;
    }

    private Info buildInfo() {
        return new Info()
                .title(properties.getTitle())
                .version(properties.getVersion())
                .description(properties.getDescription())
                .contact(buildContact())
                .license(buildLicense());
    }

    private Server buildServer() {
        return new Server()
                .url(properties.getServerUrl())
                .description("Base API Server");
    }

    private Contact buildContact() {
        return new Contact()
                .name(properties.getContact().getName())
                .email(properties.getContact().getEmail())
                .url(properties.getContact().getUrl());
    }

    private License buildLicense() {
        return new License()
                .name(properties.getLicense().getName())
                .url(properties.getLicense().getUrl());
    }
}
