package dev.abarmin.balance.app.service.processor;

import dev.abarmin.balance.common.model.Money;

public interface BalanceProcessor {
    void handle(long accountId, Money amount);
}
