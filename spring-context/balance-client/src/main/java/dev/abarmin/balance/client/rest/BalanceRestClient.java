package dev.abarmin.balance.client.rest;

import dev.abarmin.balance.client.BalanceClient;
import dev.abarmin.balance.common.model.Money;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.Map;

public class BalanceRestClient implements BalanceClient {
    private final RestClient restClient;

    public BalanceRestClient(RestClient.Builder builder,
                             BalanceRestProperties properties) {
        this.restClient = builder
                .baseUrl(properties.getEndpoint().getBaseUrl())
                .build();
    }

    @Override
    public boolean reserve(long accountId, Money amount) {
        final String response = restClient.post()
                .uri(builder -> builder
                        .path("/balance/{accountId}/reserve")
                        .build(Map.of(
                                "accountId", accountId
                        )))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(amount)
                .retrieve()
                .body(String.class);
        return Boolean.parseBoolean(response);
    }
}
