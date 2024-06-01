package com.account.balance.service;

import dev.abarmin.spring.model.Money;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {
    public boolean reserve(long accountId, Money amount) {
        return true;
    }
}
