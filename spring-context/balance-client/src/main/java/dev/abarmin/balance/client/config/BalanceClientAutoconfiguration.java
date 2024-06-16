package dev.abarmin.balance.client.config;

import dev.abarmin.balance.client.BalanceClient;
import dev.abarmin.balance.client.rest.BalanceRestClient;
import dev.abarmin.balance.client.rest.BalanceRestProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestClient;

@AutoConfiguration
@PropertySource("classpath:/balance-client-defaults.properties")
@EnableConfigurationProperties(BalanceRestProperties.class)
@ConditionalOnProperty(
        prefix = "integration.balance-service",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true)
public class BalanceClientAutoconfiguration {
    @Bean
    @ConditionalOnMissingBean
    public BalanceClient balanceRestClient(RestClient.Builder builder,
                                           BalanceRestProperties properties) {
        return new BalanceRestClient(builder, properties);
    }
}
