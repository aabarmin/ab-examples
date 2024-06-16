package dev.abarmin.balance.client.rest;

import dev.abarmin.balance.client.BalanceClient;
import dev.abarmin.balance.client.config.BalanceClientAutoconfiguration;
import dev.abarmin.balance.common.model.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestToUriTemplate;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest
@TestPropertySource(properties = {
        "integration.balance-service.endpoint.base-url=http://test"
})
@SpringJUnitConfig(BalanceClientAutoconfiguration.class)
class BalanceRestClientTest {
    @Autowired
    BalanceClient client;

    @Autowired
    MockRestServiceServer server;

    @Test
    void reserve_shouldMakeCalls() {
        server.expect(requestToUriTemplate("http://test/balance/{id}/reserve", 10L))
                .andRespond(withSuccess("true", MediaType.TEXT_PLAIN));

        client.reserve(10L, Money.of(10, "GBP"));
    }
}