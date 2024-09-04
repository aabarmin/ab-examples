package dev.abarmin.spring;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimNames;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dev.abarmin.spring.config.RSAKeyConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.security.interfaces.RSAPrivateKey;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(RSAKeyConfiguration.class)
class SignedJwtTokenGeneratorTest {
    @Autowired
    RSAPrivateKey jwtPrivateKey;

    @Test
    void generatePlainTokenAsObject() throws Exception {
        RSASSASigner signer = new RSASSASigner(jwtPrivateKey);

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .claim("sub", "Super user")
                .claim("scope", List.of("user"))
                .claim(JWTClaimNames.ISSUED_AT, Instant.now().getEpochSecond())
                .claim(JWTClaimNames.EXPIRATION_TIME, Instant.now().plus(Duration.ofHours(1)).getEpochSecond())
                .build();

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .build();

        SignedJWT token = new SignedJWT(header, claims);
        token.sign(signer);

        assertThat(token).isNotNull();

        System.out.printf("Token is: %s \n", token.serialize());
    }
}
