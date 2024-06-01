package dev.abarmin.spring.model;

import java.time.LocalDateTime;
import java.util.Collection;

public record Transaction(
        Long id,
        long accountFrom,
        long accountTo,
        Money amount,
        TransactionStatus status,
        Collection<Step> steps,
        LocalDateTime createdAt) {
}
