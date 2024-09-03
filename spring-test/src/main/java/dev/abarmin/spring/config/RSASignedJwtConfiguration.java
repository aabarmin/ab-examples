package dev.abarmin.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.security.interfaces.RSAPublicKey;

//@Configuration
public class RSASignedJwtConfiguration {
    @Bean
    public JwtDecoder decoder(RSAPublicKey jwtPublicKey) {
        return NimbusJwtDecoder
                .withPublicKey(jwtPublicKey)
                .build();
    }
}
