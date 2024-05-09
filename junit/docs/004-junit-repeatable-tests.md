# JUnit 5 Repeatable tests

The same time is executed multiple times. 

## Named repeatable test & information about the test itself

It's possible not only change name of the test but also change name of the iteration. Also,
inside the test it's possible to get information about the current test:  

```java
    @DisplayName("Name of the repeated test")
    @RepeatedTest(
            value = 10,
            name = "Iteration " + CURRENT_REPETITION_PLACEHOLDER + " of " + TOTAL_REPETITIONS_PLACEHOLDER,
            failureThreshold = 2)
    void shouldBeRepeatedMultipleTimes(TestInfo testInfo) {
        log.info("This test repeated multiple times, name is {}", testInfo.getDisplayName());
        assertThat(true).isTrue();
    }
```

In this case: 

* `@DisplayName` defines name of the test, 
* `@RepeatedTest(name = "...")` defines name of the iteration, 
* `@RepeatedTest(failureThreshold = ..)` defines number of iteration that should fail in order test
 to be assumed successful. 
* `TestInfo` gives information about the test itself. 

## Get information about the current iteration

```java
    @RepeatedTest(10)
    @DisplayName("Test that is aware that it is a repeated test")
    void awareOfExecution(RepetitionInfo repetitionInfo) {
        log.info(
                "Iteration {} of {}",
                repetitionInfo.getCurrentRepetition(),
                repetitionInfo.getTotalRepetitions());
    }
```

## Execution of iterations in parallel

By default, parallel execution is not enabled, need to create a file named `junit-platform.properties`, 
with the following parameter: 

```properties
junit.jupiter.execution.parallel.enabled=false
```

Next it'll be possible to run iterations in parallel by setting `@Execution` parameter: 

```java
    @RepeatedTest(10)
    @Execution(ExecutionMode.CONCURRENT)
    @DisplayName("Iterations are executed in parallel")
    void iterationsAreExecutedInParallel() {
        log.info("Running tests in parallel, thread {}", Thread.currentThread().getName());
    }
```