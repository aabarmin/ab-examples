package dev.abarmin.junit.basics;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;

@Slf4j
@TestClassOrder(ClassOrderer.Random.class)
public class JUnitOrderTest {
    @Nested
    @DisplayName("With default order")
    class NoOrder extends BaseTest {
    }

    @Nested
    @TestMethodOrder(MethodOrderer.Random.class)
    class WithRandomOrder extends BaseTest {
    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("With @Order annotation")
    class WithOrderAnnotation extends BaseTest {
    }

    @Nested
    @DisplayName("By method name")
    @TestMethodOrder(MethodOrderer.MethodName.class)
    class ByMethodName extends BaseTest {
    }

    @Nested
    @DisplayName("By display name")
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    class ByDisplayName extends BaseTest {
    }

    abstract class BaseTest {
        @Test
        @Order(3)
        @DisplayName("@Order(3) - First test (beta)")
        void beta_firstTest() {
            log.info("I'm the first test");
        }

        @Test
        @Order(1)
        @DisplayName("@Order(1) - Second test (alpha)")
        void alpha_secondTest() {
            log.info("I'm the second test");
        }

        @Test
        @Order(2)
        @DisplayName("@Order(2) - Third test (gamma)")
        void gamma_thirdTest() {
            log.info("I'm the third test");
        }
    }
}
