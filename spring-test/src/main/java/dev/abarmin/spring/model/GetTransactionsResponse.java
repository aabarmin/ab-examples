package dev.abarmin.spring.model;

import java.util.Collection;

public record GetTransactionsResponse(Collection<Transaction> transactions) {
}
