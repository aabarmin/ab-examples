package dev.abarmin.spring.model;

public record AuthorisationRequest(long from,
                                   long to,
                                   Money amount) {}
