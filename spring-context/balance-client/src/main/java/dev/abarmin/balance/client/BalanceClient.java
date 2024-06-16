package dev.abarmin.balance.client;

import dev.abarmin.balance.common.model.Money;

public interface BalanceClient {
    boolean reserve(long accountId, Money amount);
}
