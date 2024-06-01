package dev.abarmin.spring.model;

public record AuthoriseRequest(long fromId, long toId, Money amount) {
}
