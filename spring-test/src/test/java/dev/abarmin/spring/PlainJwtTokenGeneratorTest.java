package dev.abarmin.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.PlainHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class PlainJwtTokenGeneratorTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();

    @Test
    void generatePlainTokenAsString() throws Exception {
        Map<String, String> headers = Map.of(
                "typ", "JWT",
                "alg", "none"
        );
        Map<String, Object> body = Map.of(
                "sub", "Super user",
                "scope", List.of("user")
        );

        String headersString = objectMapper.writeValueAsString(headers);
        String bodyString = objectMapper.writeValueAsString(body);

        String token = encoder.encodeToString(headersString.getBytes(StandardCharsets.UTF_8)) +
                "." +
                encoder.encodeToString(bodyString.getBytes(StandardCharsets.UTF_8)) +
                ".";

        assertThat(token).isNotEmpty();

        System.out.printf("Token is: %s \n", token);
    }

    @Test
    void generatePlainTokenAsObject() {
        PlainHeader header = new PlainHeader();
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .claim("sub", "Super user")
                .claim("scope", List.of("user"))
                .build();

        PlainJWT token = new PlainJWT(header, claims);

        assertThat(token).isNotNull();

        System.out.printf("Token is: %s \n", token.serialize());
    }
}
