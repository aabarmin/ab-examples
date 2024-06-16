package dev.abarmin.spring;

import dev.abarmin.balance.common.model.Money;
import dev.abarmin.spring.model.AuthorisationRequest;

public class ApplicationTestHelper {
    static AuthorisationRequest createRequest() {
        return new AuthorisationRequest(1L, 2L, Money.of(10, "GBP"));
    }
}
