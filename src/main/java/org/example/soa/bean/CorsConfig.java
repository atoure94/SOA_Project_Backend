package org.example.soa.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin(" http://192.168.1.181:3000");// Origine autorisée
        config.addAllowedHeader("*"); // Autoriser tous les en-têtes
        config.addAllowedMethod("*"); // Autoriser toutes les méthodes (GET, POST, etc.)
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

