package dev.abarmin.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.SecretKey;
import java.util.List;

//@Configuration
public class AESEncryptedJwtConfiguration {
    @Bean
    public JwtDecoder jwtDecoder(SecretKey aesSecretKey) {
        return NimbusJwtDecoder
                .withSecretKey(aesSecretKey)
                .jwtProcessorCustomizer(processor -> processor.setJWEKeySelector((header, context) -> List.of(aesSecretKey)))
                .build();
    }
}
