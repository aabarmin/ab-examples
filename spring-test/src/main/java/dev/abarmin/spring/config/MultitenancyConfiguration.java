package dev.abarmin.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;

@Configuration
public class MultitenancyConfiguration {
    @Bean
    public JwtIssuerAuthenticationManagerResolver resolver() {
        return JwtIssuerAuthenticationManagerResolver
                .fromTrustedIssuers("http://localhost:9090");
    }
}
