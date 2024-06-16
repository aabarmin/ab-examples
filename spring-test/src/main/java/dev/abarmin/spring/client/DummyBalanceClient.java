package dev.abarmin.spring.client;

import dev.abarmin.balance.client.BalanceClient;
import dev.abarmin.balance.common.model.Money;

public class DummyBalanceClient implements BalanceClient {
    @Override
    public boolean reserve(long accountId, Money amount) {
        return true;
    }
}
