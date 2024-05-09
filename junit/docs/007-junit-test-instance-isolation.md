# JUnit 5 Test instance isolation

It's possible to share some data between different tests (not recommended) and it's also possible to intentionally
create a new instance of the test class every time. 

## Default behaviour

By default, for every test method, a new instance of the test class is created. 

## Instance per class

In this case, all the tests will share the same instance of the test class: 

```java
    @Nested
    @DisplayName("Share same instance")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class PerClass extends BaseTest {

    }
```

Output: 

```markdown
Repeated 1240232440
Repeated 1240232440
Repeated 1240232440
Instance 1240232440
Instance 1240232440
```

## New instance per test

Default behaviour, new instance every time: 

```java
    @Nested
    @DisplayName("New instance per test")
    @TestInstance(TestInstance.Lifecycle.PER_METHOD)
    class PerMethod extends BaseTest {

    }
```

```markdown
Repeated 1240232440
Repeated 1240232440
Repeated 1240232440
Instance 1240232440
Instance 1240232440
```