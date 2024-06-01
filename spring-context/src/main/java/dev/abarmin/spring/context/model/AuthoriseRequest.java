package dev.abarmin.spring.context.model;

public record AuthoriseRequest(
        long fromId,
        long toId,
        Amount amount) {
}
