# JUnit 5 Dynamic tests

Useful when tests should be generated based on external data.

## Simple dynamic test

```java
    @TestFactory
    @DisplayName("Simple dynamic test")
    Collection<DynamicTest> simpleDynamicTest() {
        return List.of(
                dynamicTest("First dynamic test", () -> assertThat(true).isTrue()),
                dynamicTest("Second dynamic test", () -> assertThat(false).isFalse())
        );
    }
```

## Using a dynamic container to group dynamic tests together

```java
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
```