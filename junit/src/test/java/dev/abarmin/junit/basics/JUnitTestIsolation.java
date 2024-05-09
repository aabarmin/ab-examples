package dev.abarmin.junit.basics;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@Slf4j
public class JUnitTestIsolation {
    @Nested
    @DisplayName("Default - different instances")
    class Default extends BaseTest {

    }

    @Nested
    @DisplayName("Share same instance")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class PerClass extends BaseTest {

    }

    @Nested
    @DisplayName("New instance per test")
    @TestInstance(TestInstance.Lifecycle.PER_METHOD)
    class PerMethod extends BaseTest {

    }

    abstract class BaseTest {
        @Test
        void firstTest() {
            log.info("Instance {}", this.hashCode());
        }

        @Test
        void secondTest() {
            log.info("Instance {}", this.hashCode());
        }

        @RepeatedTest(3)
        void repeated() {
            log.info("Repeated {}", this.hashCode());
        }
    }
}
