package dev.abarmin.spring.model;

import java.math.BigDecimal;

public record Money(BigDecimal amount, String currency) {
}
