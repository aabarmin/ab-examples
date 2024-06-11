package dev.abarmin.balance.app.service.processor;

import dev.abarmin.balance.common.model.Money;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
public class GreetingBalanceProcessor implements BalanceProcessor {
    private Money limit;
    private String message;

    @Override
    public void handle(long accountId, Money amount) {
        if (amount.isGreaterThan(limit)) {
            log.info(message);
        }
    }
}
