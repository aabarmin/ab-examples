package dev.abarmin.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

//@Configuration
public class AESKeyConfiguration {
    private final Path secretKeyPath = Path.of("/tmp/aes.key");

    @Bean
    public SecretKey aesSecretKey() throws Exception {
        if (Files.exists(secretKeyPath)) {
            byte[] keyContent = Files.readAllBytes(secretKeyPath);
            return new SecretKeySpec(keyContent, "AES");
        }

        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(256);

        SecretKey secretKey = generator.generateKey();

        Files.write(secretKeyPath, secretKey.getEncoded(), StandardOpenOption.CREATE_NEW);

        return secretKey;
    }
}
