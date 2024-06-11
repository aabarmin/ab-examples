package dev.abarmin.balance.app.service;

import dev.abarmin.balance.app.service.processor.BalanceProcessor;
import dev.abarmin.balance.common.model.Money;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Slf4j
@Setter
public class BalanceService {
    private String helloMessage;

    @Autowired
    private Collection<BalanceProcessor> processors;

    public boolean reserve(long accountId, Money amount) {
        if (StringUtils.isNoneEmpty(helloMessage)) {
            log.info(helloMessage);
        }
        processors.forEach(p -> p.handle(accountId, amount));
        return true;
    }
}
