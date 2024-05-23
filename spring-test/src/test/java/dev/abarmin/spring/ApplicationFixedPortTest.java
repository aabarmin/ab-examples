package dev.abarmin.spring;

import dev.abarmin.spring.client.BalanceServiceClient;
import dev.abarmin.spring.model.GetTransactionsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.URI;

import static dev.abarmin.spring.ApplicationTestHelper.createRequest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        properties = {"server.port=8082"},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ApplicationFixedPortTest {
    @TestConfiguration
    static class MyConfig {
        @MockBean
        BalanceServiceClient balanceServiceClient;
    }

    @Test
    void createAndRetrieve(@Autowired TestRestTemplate restTemplate) throws Exception {
        URI location = restTemplate.postForLocation("/transactions", createRequest());
        assertThat(location.toString()).contains("/transactions");

        GetTransactionsResponse transactions = restTemplate.getForObject("http://localhost:8082/transactions", GetTransactionsResponse.class);

        assertThat(transactions).isNotNull();
        assertThat(transactions.transactions()).isNotEmpty();
    }
}
