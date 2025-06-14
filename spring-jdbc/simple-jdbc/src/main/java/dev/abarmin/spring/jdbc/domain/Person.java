package dev.abarmin.spring.jdbc.domain;

import java.time.Instant;

public record Person(
        Integer id,
        String name,
        Instant createdAt) {

    public Person(String name) {
        this(null, name, Instant.now());
    }

    public Person withId(int id) {
        return new Person(id, name, createdAt);
    }

}
