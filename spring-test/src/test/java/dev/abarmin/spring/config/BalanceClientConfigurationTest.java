package dev.abarmin.spring.config;

import dev.abarmin.balance.client.BalanceClient;
import dev.abarmin.balance.client.config.BalanceClientAutoconfiguration;
import dev.abarmin.balance.client.rest.BalanceRestClient;
import dev.abarmin.spring.client.DummyBalanceClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;

class BalanceClientConfigurationTest {
    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withUserConfiguration(
                    BalanceClientConfiguration.class,
                    BalanceClientAutoconfiguration.class
            )
            .withBean(RestClient.Builder.class, () -> RestClient.builder());

    @Test
    void byDefaultHasBalanceClientFromStarter() {
        runner.run(context -> {
            assertThat(context).hasSingleBean(BalanceClient.class);
            assertThat(context).getBean(BalanceClient.class)
                    .isInstanceOf(BalanceRestClient.class);
        });
    }

    @Test
    void customProfile_shouldHaveDummyBalanceClient() {
        runner.withPropertyValues("spring.profiles.active=use-dummy-balance-client")
                .run(context -> {
                    assertThat(context).hasSingleBean(BalanceClient.class);
                    assertThat(context).getBean(BalanceClient.class)
                            .isInstanceOf(DummyBalanceClient.class);
                });
    }
}