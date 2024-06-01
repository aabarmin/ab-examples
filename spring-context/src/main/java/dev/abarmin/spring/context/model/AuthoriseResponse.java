package dev.abarmin.spring.context.model;

public record AuthoriseResponse(
        long transactionId,
        Amount authorisedAmount
) {
}
