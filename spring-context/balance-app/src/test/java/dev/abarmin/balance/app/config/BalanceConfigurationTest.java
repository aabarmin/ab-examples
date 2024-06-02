package dev.abarmin.balance.app.config;

import dev.abarmin.balance.app.controller.BalanceHandler;
import dev.abarmin.balance.app.service.BalanceService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.annotation.UserConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class BalanceConfigurationTest {
    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withConfiguration(UserConfigurations.of(
                    BalanceConfiguration.class,
                    ObjectMapperConfiguration.class));

    @Test
    void configuration_hasBalanceBeans() {
        runner.run(context -> {
            assertThat(context).hasSingleBean(BalanceHandler.class);
            assertThat(context).hasSingleBean(BalanceService.class);
        });
    }
}