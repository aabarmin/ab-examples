package dev.abarmin.spring.model;

public record CreateTransactionRequest(long from,
                                       long to,
                                       Money amount) {

    public Transaction toTransaction() {
        return new Transaction(null, from, to, amount, TransactionStatus.AUTHORISED);
    }
}
