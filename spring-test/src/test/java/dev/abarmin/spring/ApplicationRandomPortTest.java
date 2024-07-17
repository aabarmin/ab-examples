package dev.abarmin.spring;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import dev.abarmin.spring.model.GetTransactionsResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.URI;

import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.status;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationRandomPortTest {
    @Container
    static PostgreSQLContainer DB = new PostgreSQLContainer();

    @RegisterExtension
    static WireMockExtension wireMock = WireMockExtension.newInstance()
            .options(wireMockConfig()
                    .http2PlainDisabled(true)
                    .dynamicPort())
            .build();

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("integration.balance-service.endpoint.base-url", wireMock::baseUrl);
        registry.add("spring.datasource.url", DB::getJdbcUrl);
        registry.add("spring.datasource.username", DB::getUsername);
        registry.add("spring.datasource.password", DB::getPassword);
        registry.add("spring.test.database.replace", () -> false);
    }

    @Test
    void createAndRetrieve(@Autowired TestRestTemplate restTemplate) {
        wireMock.stubFor(post(urlMatching("/balance/(\\d+)/reserve"))
                .willReturn(status(200).withBody("true")));

        URI location = restTemplate.postForLocation("/transactions", ApplicationTestHelper.createRequest());
        assertThat(location.toString()).contains("/transactions");

        GetTransactionsResponse transactions = restTemplate.getForObject("/transactions", GetTransactionsResponse.class);

        assertThat(transactions).isNotNull();
        assertThat(transactions.transactions()).isNotEmpty();

        wireMock.verify(postRequestedFor(urlMatching("/balance/(\\d+)/reserve")));
    }
}