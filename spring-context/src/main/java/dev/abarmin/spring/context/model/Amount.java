package dev.abarmin.spring.context.model;

import java.math.BigDecimal;

public record Amount(BigDecimal amount, Currency currency) {
}
