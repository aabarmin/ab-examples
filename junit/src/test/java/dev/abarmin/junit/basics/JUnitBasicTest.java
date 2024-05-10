package dev.abarmin.junit.basics;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class JUnitBasicTest {
    @Test
    @DisplayName("I'm a very basic test, nothing interesting")
    void veryBasicTest() {
        log.info("I'm a very basic test");
        assertThat(true).isTrue();
    }

    @Test
    @Disabled
    @DisplayName("Test with timeout")
    @Timeout(value = 42, unit = TimeUnit.MICROSECONDS)
    void testWithTimeout() {
        log.info("I should be a very slow test but I'm quick");
    }
}
