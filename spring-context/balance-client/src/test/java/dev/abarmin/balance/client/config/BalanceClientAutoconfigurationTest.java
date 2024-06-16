package dev.abarmin.balance.client.config;

import dev.abarmin.balance.client.BalanceClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;

class BalanceClientAutoconfigurationTest {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(BalanceClientAutoconfiguration.class)
            .withBean(RestClient.Builder.class, () -> RestClient.builder());

    @Test
    void shouldHaveClientByDefault() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(BalanceClient.class);
        });
    }

    @Test
    void whenDisabledShouldNotHaveBeans() {
        contextRunner
                .withPropertyValues("integration.balance-service.enabled=false")
                .run(context -> {
                    assertThat(context).doesNotHaveBean(BalanceClient.class);
                });
    }
}