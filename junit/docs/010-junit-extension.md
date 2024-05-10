# JUnit 5 Extensions

## Lifecycle extensions

To create an extension, create a new class, implement an `Extension` marker interface and some of
lifecycle callback interfaces like `BeforeEachCallback`:

```java
public class SimpleLifecycleExtension implements Extension,
        BeforeEachCallback, AfterEachCallback,
        BeforeAllCallback, AfterAllCallback,
        BeforeTestExecutionCallback, AfterTestExecutionCallback {
        
    // methods from interfaces are implemented here
}
```

## Method injection extensions

To inject values into methods implement both `Extension` and `ParameterResolver` interfaces: 

```java
public class MyParameterExtension implements Extension, ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        // should be injected
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        // what to inject
    }
}
```