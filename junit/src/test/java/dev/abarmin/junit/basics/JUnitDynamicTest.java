package dev.abarmin.junit.basics;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicContainer;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@Slf4j
@DisplayName("Dynamic tests")
class JUnitDynamicTest {
    @TestFactory
    @DisplayName("Simple dynamic test")
    Collection<DynamicTest> simpleDynamicTest() {
        return List.of(
                dynamicTest("First dynamic test", () -> assertThat(true).isTrue()),
                dynamicTest("Second dynamic test", () -> assertThat(false).isFalse())
        );
    }

    @TestFactory
    @DisplayName("Containers of dynamic tests")
    Collection<DynamicContainer> simpleDynamicContainers() {
        return List.of(
                dynamicContainer("First container", IntStream
                        .range(0, 10)
                        .mapToObj(index -> dynamicTest("Test " + index, () -> assertThat(true).isTrue()))),
                dynamicContainer("Second container", IntStream
                        .range(0, 20)
                        .mapToObj(index -> dynamicTest("Test " + index, () -> assertThat(true).isTrue())))
        );
    }
}
