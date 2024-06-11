package dev.abarmin.balance.common.model;

import java.math.BigDecimal;
import java.util.Currency;

public record Money(BigDecimal amount,
                    Currency currency) {

    public boolean isGreaterThan(Money another) {
        if (currency != another.currency) {
            return false;
        }
        return amount.compareTo(another.amount) > 0;
    }
}
