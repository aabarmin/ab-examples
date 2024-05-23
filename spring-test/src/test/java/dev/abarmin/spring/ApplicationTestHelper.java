package dev.abarmin.spring;

import dev.abarmin.spring.model.CreateTransactionRequest;
import dev.abarmin.spring.model.Money;

public class ApplicationTestHelper {
    static CreateTransactionRequest createRequest() {
        return new CreateTransactionRequest(1L, 2L, Money.of(10, "GBP"));
    }
}
