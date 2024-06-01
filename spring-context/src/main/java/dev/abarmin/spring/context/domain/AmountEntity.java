package dev.abarmin.spring.context.domain;

import dev.abarmin.spring.context.model.Currency;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public class AmountEntity {
    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "CURRENCY")
    private Currency currency;
}
