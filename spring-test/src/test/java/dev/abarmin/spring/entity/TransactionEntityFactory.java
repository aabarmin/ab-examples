package dev.abarmin.spring.entity;

import dev.abarmin.spring.model.TransactionStatus;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.Set;

@UtilityClass
public class TransactionEntityFactory {
    public static TransactionEntity createAuthorised(long from, long to, int amount) {
        final TransactionEntity transaction = TransactionEntity.builder()
                .accountFrom(from)
                .accountTo(to)
                .status(TransactionStatus.AUTHORISED)
                .amount(MoneyEntity.builder()
                        .amount(BigDecimal.valueOf(amount))
                        .currency("GBP")
                        .build())
                .build();

        transaction.setSteps(Set.of(
                StepEntity.builder()
                        .amount(MoneyEntity.builder()
                                .amount(BigDecimal.valueOf(amount))
                                .currency("GBP")
                                .build())
                        .transaction(transaction)
                        .build()
        ));

        return transaction;
    }
}