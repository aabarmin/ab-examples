package dev.abarmin.junit.basics;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.RepeatedTest.CURRENT_REPETITION_PLACEHOLDER;
import static org.junit.jupiter.api.RepeatedTest.TOTAL_REPETITIONS_PLACEHOLDER;

@Slf4j
class JUnitRepeatableTest {
    @BeforeEach
    void setUp() {
        log.info("Executed before every iteration");
    }

    @AfterEach
    void tearDown() {
        log.info("Executed after every iteration");
    }

    @DisplayName("Name of the repeated test")
    @RepeatedTest(
            value = 10,
            name = "Iteration " + CURRENT_REPETITION_PLACEHOLDER + " of " + TOTAL_REPETITIONS_PLACEHOLDER,
            failureThreshold = 2)
    void shouldBeRepeatedMultipleTimes(TestInfo testInfo) {
        log.info("This test repeated multiple times, name is {}", testInfo.getDisplayName());
        assertThat(true).isTrue();
    }

    @RepeatedTest(10)
    @DisplayName("Test that is aware that it is a repeated test")
    void awareOfExecution(RepetitionInfo repetitionInfo) {
        log.info(
                "Iteration {} of {}",
                repetitionInfo.getCurrentRepetition(),
                repetitionInfo.getTotalRepetitions());
    }

    @RepeatedTest(10)
    @Execution(ExecutionMode.CONCURRENT)
    @DisplayName("Iterations are executed in parallel")
    void iterationsAreExecutedInParallel() {
        log.info("Running tests in parallel, thread {}", Thread.currentThread().getName());
    }
}
