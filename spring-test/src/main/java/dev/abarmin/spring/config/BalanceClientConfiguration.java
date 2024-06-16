package dev.abarmin.spring.config;

import dev.abarmin.balance.client.BalanceClient;
import dev.abarmin.balance.client.config.BalanceClientAutoconfiguration;
import dev.abarmin.spring.client.DummyBalanceClient;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@AutoConfigureBefore(BalanceClientAutoconfiguration.class)
public class BalanceClientConfiguration {
    @Bean
    @Profile("use-dummy-balance-client")
    public BalanceClient dummyBalanceServiceClient() {
        return new DummyBalanceClient();
    }
}
