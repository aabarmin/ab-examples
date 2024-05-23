package dev.abarmin.spring.client;

import dev.abarmin.spring.model.Money;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface BalanceServiceClient {
    @PostExchange("/balance/{accountId}/reserve")
    void reserve(@PathVariable Long accountId, @RequestBody Money amount);
}
