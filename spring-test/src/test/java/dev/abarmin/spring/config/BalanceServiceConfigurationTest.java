package dev.abarmin.spring.config;

import dev.abarmin.spring.client.BalanceServiceClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.annotation.Configurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BalanceServiceConfigurationTest {
    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withUserConfiguration(BalanceServiceConfiguration.class)
            .withBean(BalanceServiceProperties.class)
            .withBean(RestClient.Builder.class, () -> RestClient.builder());

    @Test
    void configuration_hasBalanceServiceClient() {
        runner.run(context -> {
            assertThat(context).hasSingleBean(BalanceServiceClient.class);
        });
    }
}