package dev.abarmin.spring.client;

import dev.abarmin.spring.config.BalanceServiceConfiguration;
import dev.abarmin.spring.config.BalanceServiceProperties;
import dev.abarmin.spring.model.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestToUriTemplate;

@RestClientTest
@SpringJUnitConfig(BalanceServiceConfiguration.class)
@EnableConfigurationProperties(BalanceServiceProperties.class)
@TestPropertySource(properties = {
        "integration.balance-service.endpoint.base-url="
})
class BalanceServiceClientTest {
    @Autowired
    BalanceServiceClient client;

    @Autowired
    MockRestServiceServer server;

    @Test
    void reserve_shouldMakeCalls() {
        server.expect(requestToUriTemplate("/balance/{id}/reserve", 10L));

        client.reserve(10L, Money.of(10, "GBP"));
    }
}