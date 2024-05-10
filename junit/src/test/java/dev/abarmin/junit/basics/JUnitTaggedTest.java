package dev.abarmin.junit.basics;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Slf4j
@DisplayName("Tests with tags")
class JUnitTaggedTest {
    @Test
    @Tag("Slow")
    @DisplayName("First slow test")
    void firstSlowTest() {
        log.info("First slow test");
    }

    @Test
    @Tag("Slow")
    @DisplayName("Second slow test")
    void secondSlowTest() {
        log.info("Second slow test");
    }

    @Test
    @Tag("Quick")
    void firstQuickTest() {
        log.info("First quick test");
    }

    @Test
    @Tag("Quick")
    void secondQuickTest() {
        log.info("Second quick test");
    }
}
