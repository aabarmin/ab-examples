package dev.abarmin.balance.common.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

class MoneyTest {
    @Test
    void isGreaterThan_whenGreaterShouldReturnTrue() {
        Money first = new Money(BigDecimal.TEN, Currency.getInstance("USD"));
        Money second = new Money(BigDecimal.TWO, Currency.getInstance("USD"));

        assertThat(first.isGreaterThan(second)).isTrue();
    }
}