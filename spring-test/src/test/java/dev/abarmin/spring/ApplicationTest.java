package dev.abarmin.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.abarmin.spring.model.CreateTransactionRequest;
import dev.abarmin.spring.model.GetTransactionsResponse;
import dev.abarmin.spring.model.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ApplicationTest {
    @Nested
    @SpringBootTest
    @AutoConfigureMockMvc
    @DisplayName("Test with mock web environment")
    class MockEnvironment {
        @Autowired
        MockMvc mockMvc;

        @Autowired
        ObjectMapper objectMapper;

        @Test
        void createAndRetrieve() throws Exception {
            String bodyJson = objectMapper.writeValueAsString(createRequest());

            mockMvc.perform(post("/transactions")
                    .content(bodyJson)
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isCreated())
                    .andExpect(header().exists(HttpHeaders.LOCATION));

            mockMvc.perform(get("/transactions"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.transactions").isArray())
                    .andExpect(jsonPath("$.transactions[0].id").isNumber());
        }
    }

    @Nested
    @SpringBootTest(properties = {"server.port=8082"}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
    @DisplayName("Test with real web environment, fixed port")
    class FixedPort {
        @Test
        void createAndRetrieve(@Autowired TestRestTemplate restTemplate) throws Exception {
            URI location = restTemplate.postForLocation("http://localhost:8082/transactions", createRequest());
            assertThat(location.toString()).contains("/transactions");

            GetTransactionsResponse transactions = restTemplate.getForObject("http://localhost:8082/transactions", GetTransactionsResponse.class);

            assertThat(transactions).isNotNull();
            assertThat(transactions.transactions()).isNotEmpty();
        }
    }

    @Nested
    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    @DisplayName("Test with real web environment, random port")
    class RandomPort {
        @LocalServerPort
        int serverPort;

        @Test
        void createAndRetrieve(@Autowired TestRestTemplate restTemplate) {
            URI location = restTemplate.postForLocation("/transactions", createRequest());
            assertThat(location.toString()).contains("/transactions");

            GetTransactionsResponse transactions = restTemplate.getForObject("/transactions", GetTransactionsResponse.class);

            assertThat(transactions).isNotNull();
            assertThat(transactions.transactions()).isNotEmpty();
        }
    }

    static CreateTransactionRequest createRequest() {
        return new CreateTransactionRequest(1L, 2L, Money.of(10, "GBP"));
    }
}