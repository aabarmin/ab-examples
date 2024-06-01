package dev.abarmin.spring.client;

import dev.abarmin.spring.model.Money;

public class DummyBalanceServiceClient implements BalanceServiceClient {
    @Override
    public void reserve(Long accountId, Money amount) {
        // does nothing
    }
}
