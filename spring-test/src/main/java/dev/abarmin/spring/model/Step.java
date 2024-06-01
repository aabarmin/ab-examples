package dev.abarmin.spring.model;

import java.time.LocalDateTime;

public record Step(
        Long id,
        Money amount,
        LocalDateTime createdAt) {
}
