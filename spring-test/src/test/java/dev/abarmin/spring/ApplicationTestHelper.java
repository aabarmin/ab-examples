package dev.abarmin.spring;

import dev.abarmin.spring.model.AuthorisationRequest;
import dev.abarmin.spring.model.Money;

public class ApplicationTestHelper {
    static AuthorisationRequest createRequest() {
        return new AuthorisationRequest(1L, 2L, Money.of(10, "GBP"));
    }
}
