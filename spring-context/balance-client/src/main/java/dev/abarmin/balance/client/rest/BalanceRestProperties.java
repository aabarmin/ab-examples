package dev.abarmin.balance.client.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties("integration.balance-service")
public class BalanceRestProperties {
    private boolean enabled = true;

    @Valid
    private Endpoint endpoint = new Endpoint();

    @Data
    static class Endpoint {
        @NotEmpty
        private String baseUrl;
    }
}
