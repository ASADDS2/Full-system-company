package com.fullsystem.fullsystem.infrastructure.config;

import org.springframework.context.annotation.Configuration;

/**
 * Resilience4j configuration.
 * 
 * Note: Circuit Breaker instances are configured via application.yaml
 * properties
 * using the resilience4j-spring-boot3 starter.
 * 
 * Example configuration in yaml:
 * resilience4j:
 * circuitbreaker:
 * instances:
 * externalService:
 * sliding-window-size: 10
 */
@Configuration
public class ResilienceConfig {
        // Configuration provided via application.yaml and auto-configuration
}
