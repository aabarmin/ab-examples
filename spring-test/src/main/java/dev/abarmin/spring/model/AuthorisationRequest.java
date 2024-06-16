package dev.abarmin.spring.model;

import dev.abarmin.balance.common.model.Money;

public record AuthorisationRequest(long from,
                                   long to,
                                   Money amount) {}
