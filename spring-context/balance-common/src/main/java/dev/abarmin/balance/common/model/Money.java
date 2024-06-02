package dev.abarmin.balance.common.model;

import java.math.BigDecimal;
import java.util.Currency;

public record Money(BigDecimal amount,
                    Currency currency) {
}
