package com.fullsystem.fullsystem.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

/**
 * CORS configuration for the application.
 * Allows cross-origin requests from configured origins.
 */
@Configuration
public class CorsConfig {

    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

    @Value("${cors.allowed-methods}")
    private String allowedMethods;

    @Value("${cors.allowed-headers}")
    private String allowedHeaders;

    @Value("${cors.allow-credentials}")
    private boolean allowCredentials;

    /**
     * Configures CORS filter with settings from application.yml.
     * 
     * @return configured CORS filter
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(allowCredentials);
        config.setAllowedOrigins(parseList(allowedOrigins));
        config.setAllowedHeaders(parseList(allowedHeaders));
        config.setAllowedMethods(parseList(allowedMethods));

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    /**
     * Parses comma-separated string to list.
     * 
     * @param value comma-separated string
     * @return list of values
     */
    private List<String> parseList(String value) {
        return Arrays.asList(value.split(","));
    }
}
