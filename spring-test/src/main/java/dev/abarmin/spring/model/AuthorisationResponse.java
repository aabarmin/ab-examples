package dev.abarmin.spring.model;

import dev.abarmin.balance.common.model.Money;

public record AuthorisationResponse(
        Long transactionId,
        Money amount) {
}
