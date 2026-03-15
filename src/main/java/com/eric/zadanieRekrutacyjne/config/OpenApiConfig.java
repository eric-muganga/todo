package com.eric.zadanieRekrutacyjne.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Todo API")
                        .version("1.0.0")
                        .description("REST API for managing tasks. Built with Spring Boot 3, PostgreSQL, and Flyway."));
    }

    @Bean
    public OpenApiCustomizer pageableOpenApiCustomizer() {
        return openApi -> {
            if (openApi.getComponents() != null && openApi.getComponents().getSchemas() != null) {
                openApi.getComponents().getSchemas().remove("Pageable");
                openApi.getComponents().getSchemas().remove("PageableObject");
                openApi.getComponents().getSchemas().remove("SortObject");
                openApi.getComponents().getSchemas().remove("Page");
                openApi.getComponents().getSchemas().remove("PageTaskResponse");
            }
        };
    }
}
