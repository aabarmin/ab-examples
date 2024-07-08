package dev.abarmin.spring.entity;

import dev.abarmin.spring.model.TransactionStatus;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class TransactionEntityFactory {
    public static TransactionEntity createAuthorised(long from, long to, int amount) {
        return TransactionEntity.builder()
                .accountFrom(from)
                .accountTo(to)
                .status(TransactionStatus.AUTHORISED)
                .amount(MoneyEntity.builder()
                        .amount(BigDecimal.valueOf(amount))
                        .currency("GBP")
                        .build())
                .build();
    }
}