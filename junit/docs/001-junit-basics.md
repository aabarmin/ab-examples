# JUnit 5 Basics

## Change name of the test

```java
    @Test
    @DisplayName("I'm a very basic test, nothing interesting")
    void veryBasicTest() {
        // ...
    }
```

## Test with timeout

```java
    @Test
    @DisplayName("Test with timeout")
    @Timeout(value = 42, unit = TimeUnit.MICROSECONDS)
    void testWithTimeout() {
        
    }
```