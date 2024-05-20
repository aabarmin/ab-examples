package dev.abarmin.spring.model;

public record Transaction(Long id, long accountFrom, long accountTo, Money amount) {
}
