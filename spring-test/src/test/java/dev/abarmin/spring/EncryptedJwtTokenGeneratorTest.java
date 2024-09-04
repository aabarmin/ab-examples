package dev.abarmin.spring;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.crypto.AESEncrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimNames;
import com.nimbusds.jwt.JWTClaimsSet;
import dev.abarmin.spring.config.AESKeyConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(AESKeyConfiguration.class)
class EncryptedJwtTokenGeneratorTest {
    @Autowired
    SecretKey secretKey;

    @Test
    void generateEncryptedToken() throws Exception {
        AESEncrypter encrypter = new AESEncrypter(secretKey);

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .claim("sub", "Super user")
                .claim("scope", List.of("user"))
                .claim(JWTClaimNames.ISSUED_AT, Instant.now().getEpochSecond())
                .claim(JWTClaimNames.EXPIRATION_TIME, Instant.now().plus(Duration.ofHours(1)).getEpochSecond())
                .build();

        JWEHeader header = new JWEHeader.Builder(JWEAlgorithm.A256KW, EncryptionMethod.A256GCM)
                .build();

        EncryptedJWT token = new EncryptedJWT(header, claims);
        token.encrypt(encrypter);

        assertThat(token).isNotNull();

        System.out.println(token.serialize());
    }
}
