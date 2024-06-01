package dev.abarmin.spring.model;

public record AuthorisationResponse(
        Long transactionId,
        Money amount) {
}
