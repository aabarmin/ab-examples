package dev.abarmin.spring.config;

import com.nimbusds.jose.HeaderParameterNames;
import com.nimbusds.jose.PlainHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

//@Configuration
public class PlainJwtConfiguration {
    @Bean
    public JwtDecoder plainDecoder() {
        return new JwtDecoder() {
            @Override
            @SneakyThrows
            public Jwt decode(String token) throws JwtException {
                PlainJWT plainToken = PlainJWT.parse(token);
                PlainHeader plainHeader = plainToken.getHeader();
                JWTClaimsSet plainClaims = plainToken.getJWTClaimsSet();

                return Jwt
                        .withTokenValue(token)
                        .headers(headers -> {
                            headers.put(HeaderParameterNames.ALGORITHM, plainHeader.getAlgorithm());
                            headers.put(HeaderParameterNames.TYPE, plainHeader.getType());
                        })
                        .claims(claims -> claims.putAll(plainClaims.getClaims()))
                        .build();
            }
        };
    }
}
