package dev.abarmin.balance.app.service.processor;

import dev.abarmin.balance.common.model.Money;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingBalanceProcessor implements BalanceProcessor {
    @Override
    public void handle(long accountId, Money amount) {
        log.info("Received request to reserve {} on account {}", amount, accountId);
    }
}
