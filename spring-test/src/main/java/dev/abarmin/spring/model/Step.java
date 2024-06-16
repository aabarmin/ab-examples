package dev.abarmin.spring.model;

import dev.abarmin.balance.common.model.Money;

import java.time.LocalDateTime;

public record Step(
        Long id,
        Money amount,
        LocalDateTime createdAt) {
}
