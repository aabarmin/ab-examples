package dev.abarmin.spring;

import dev.abarmin.balance.client.BalanceClient;
import dev.abarmin.spring.model.GetTransactionsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;

import static dev.abarmin.spring.ApplicationTestHelper.createRequest;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@SpringBootTest(
        properties = {"server.port=8082"},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ApplicationFixedPortTest {
    @TestConfiguration
    static class MyConfig {
        @MockBean
        BalanceClient balanceClient;
    }

    @Test
    void createAndRetrieve(@Autowired TestRestTemplate restTemplate) {
        URI location = restTemplate.postForLocation("/transactions", createRequest());
        assertThat(location.toString()).contains("/transactions");

        GetTransactionsResponse transactions = restTemplate.getForObject("http://localhost:8082/transactions", GetTransactionsResponse.class);

        assertThat(transactions).isNotNull();
        assertThat(transactions.transactions()).isNotEmpty();
    }
}
