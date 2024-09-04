package dev.abarmin.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Configuration
public class RSAKeyConfiguration {
    private final Path publicKeyPath = Path.of("/tmp/public.key");
    private final Path privateKeyPath = Path.of("/tmp/private.key");

    @Bean
    public RSAPublicKey jwtPublicKey() throws Exception {
        if (Files.exists(publicKeyPath)) {
            byte[] keyBytes = Files.readAllBytes(publicKeyPath);
            return (RSAPublicKey) KeyFactory.getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(keyBytes));
        }

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(4096);
        KeyPair keyPair = generator.generateKeyPair();

        Files.write(publicKeyPath, keyPair.getPublic().getEncoded(), StandardOpenOption.CREATE_NEW);
        Files.write(privateKeyPath, keyPair.getPrivate().getEncoded(), StandardOpenOption.CREATE_NEW);

        return (RSAPublicKey) keyPair.getPublic();
    }

    @Bean
    @DependsOn("jwtPublicKey")
    public RSAPrivateKey jwtPrivateKey() throws Exception {
        byte[] keyBytes = Files.readAllBytes(privateKeyPath);
        return (RSAPrivateKey) KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
    }
}
