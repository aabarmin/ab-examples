package dev.abarmin.spring.config;

import dev.abarmin.spring.client.BalanceServiceClient;
import dev.abarmin.spring.client.DummyBalanceServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration(proxyBeanMethods = false)
public class BalanceServiceConfiguration {
    @Bean
    @Profile("production")
    public BalanceServiceClient balanceServiceClient(BalanceServiceProperties properties,
                                                     RestClient.Builder builder) {
        final RestClient restClient = builder
                .baseUrl(properties.getEndpoint().getBaseUrl())
                .build();

        final HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build();

        return proxyFactory.createClient(BalanceServiceClient.class);
    }

    @Bean
    @Profile("default")
    public BalanceServiceClient dummyBalanceServiceClient() {
        return new DummyBalanceServiceClient();
    }
}
