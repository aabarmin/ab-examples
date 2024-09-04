package dev.abarmin.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
public class JwtScopeConfiguration {
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authorityConverter = new JwtGrantedAuthoritiesConverter();
        authorityConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter scopeConverter = new JwtAuthenticationConverter();
        scopeConverter.setJwtGrantedAuthoritiesConverter(authorityConverter);
        return scopeConverter;
    }
}
