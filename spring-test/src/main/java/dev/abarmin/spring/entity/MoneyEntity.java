package dev.abarmin.spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class MoneyEntity {
    @Column(name = "AMOUNT_VALUE")
    private BigDecimal amount;

    @Column(name = "AMOUNT_CURRENCY")
    private String currency;
}
