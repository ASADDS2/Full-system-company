package com.fullsystem.fullsystem.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI (Swagger) configuration for API documentation.
 * Provides interactive API documentation accessible at /swagger-ui.html
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configures OpenAPI documentation metadata.
     * 
     * @return OpenAPI configuration
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Full System Quotation API")
                        .version("1.0.0")
                        .description(
                                "REST API for managing service quotations with automatic cost calculation based on business rules")
                        .contact(new Contact()
                                .name("Full System")
                                .email("support@fullsystem.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}
