# JUnit 5 Test order

Let's assume there are the following tests in the base class: 

```java
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
```

## Default order

Tests are executed in the order methods declared in the class: 

```java
    @Nested
    @DisplayName("With default order")
    class NoOrder extends BaseTest {
    }
```

```markdown
* @Order(3) - First test (beta)
* @Order(1) - Second test (alpha)
* @Order(2) - Third test (gamma)
```

## Random order

Tests are executed in the random order

```java
    @Nested
    @TestMethodOrder(MethodOrderer.Random.class)
    class WithRandomOrder extends BaseTest {
    }
```

```markdown
* @Order(2) - Third test (gamma)
* @Order(3) - First test (beta)
* @Order(1) - Second test (alpha)
```

## Order is defined by `@Order` annotation

Self-explanatory, order is defined by `@Order` annotation: 

```java
    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("With @Order annotation")
    class WithOrderAnnotation extends BaseTest {
    }
```

```markdown
* @Order(1) - Second test (alpha)
* @Order(2) - Third test (gamma)
* @Order(3) - First test (beta)
```

## By name of the method

By default, sorted alphabetically: 

```java
    @Nested
    @DisplayName("By method name")
    @TestMethodOrder(MethodOrderer.MethodName.class)
    class ByMethodName extends BaseTest {
    }
```

```markdown
* @Order(1) - Second test (alpha)
* @Order(3) - First test (beta)
* @Order(2) - Third test (gamma)
```

## By display name

By default, sorted alphabetically but in this case by display name: 

```java
    @Nested
    @DisplayName("By display name")
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    class ByDisplayName extends BaseTest {
    }
```

```markdown
* @Order(1) - Second test (alpha)
* @Order(2) - Third test (gamma)
* @Order(3) - First test (beta)
```

## Order of nested tests

If the test has nested classes, it's possible to execute nested tests in defined order, for example, 
in the random order: 

```java
@Slf4j
@TestClassOrder(ClassOrderer.Random.class)
public class JUnitOrderTest {
    // tests with @Nested annotation
}
```