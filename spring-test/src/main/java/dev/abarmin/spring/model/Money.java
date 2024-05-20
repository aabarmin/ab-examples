package dev.abarmin.spring.model;

import java.math.BigDecimal;
import java.util.Currency;

public record Money(BigDecimal amount,
                    Currency currency) {

    public static Money of(int amount, String currency) {
        return new Money(BigDecimal.valueOf(amount), Currency.getInstance(currency));
    }
}
