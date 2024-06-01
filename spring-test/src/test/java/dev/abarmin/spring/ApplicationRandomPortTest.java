package dev.abarmin.spring;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import dev.abarmin.spring.model.GetTransactionsResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.net.URI;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("production")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationRandomPortTest {
    @RegisterExtension
    static WireMockExtension wireMock = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add(
                "integration.balance-service.endpoint.base-url",
                () -> wireMock.baseUrl()
        );
    }

    @Test
    void createAndRetrieve(@Autowired TestRestTemplate restTemplate) {
        wireMock.stubFor(post(urlMatching("/balance/(\\d+)/reserve"))
                .willReturn(status(200)));

        URI location = restTemplate.postForLocation("/transactions", ApplicationTestHelper.createRequest());
        assertThat(location.toString()).contains("/transactions");

        GetTransactionsResponse transactions = restTemplate.getForObject("/transactions", GetTransactionsResponse.class);

        assertThat(transactions).isNotNull();
        assertThat(transactions.transactions()).isNotEmpty();

        wireMock.verify(postRequestedFor(urlMatching("/balance/(\\d+)/reserve")));
    }
}