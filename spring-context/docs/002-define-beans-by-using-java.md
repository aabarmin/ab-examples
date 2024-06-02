# Define Spring bean using Java config

## Define Spring Configurations that will provide bean definitions

Instead of enumerating beans it is possible to enumerate Spring's components that provide bean
definitions. Useful approach when it is necessary to integrate non-Spring code with the Spring app.

```java
ApplicationContext context = new AnnotationConfigApplicationContext(
        BalanceConfiguration.class,
        ObjectMapperConfiguration.class);
```

Configuration classes for now may not have any Spring's annotations on class level as referred directly.

```java
public class BalanceConfiguration {
    @Bean
    public BalanceService balanceService() {
        return new BalanceService();
    }

    @Bean
    public BalanceHandler balanceHandler(ObjectMapper objectMapper,
                                         BalanceService balanceService) {
        final BalanceHandler handler = new BalanceHandler();
        handler.setBalanceService(balanceService);
        handler.setObjectMapper(objectMapper);
        return handler;
    }
}
```

It is worth mentioning that dependency injection is still happening manually as Spring is not aware that
`BalanceHandler` requires `ObjectMapper` and `BalanceService` to operate. 

It is possible to fix it by adding `@Autowired` annotation on properties: 

```java
public class BalanceHandler implements HttpHandler {
    @Autowired
    private BalanceService balanceService;

    @Autowired
    private ObjectMapper objectMapper;
    
    // other code goes here
}
```

## Enumerate all the beans

It's a simple but odd approach, nevertheless, it allows to make a step away from XML. 

```java
ApplicationContext context = new AnnotationConfigApplicationContext(
        ObjectMapper.class,
        BalanceHandler.class,
        BalanceService.class
);
```

## Define bean by using Spring annotations

The approach used now in Spring Boot applications uses Spring's annotations like `@Component` to 
mark classes that should be managed by Spring Framework. In this case it is necessary to define a package
that is considered as a root package for discovering beans. 

```java
ApplicationContext context = new AnnotationConfigApplicationContext("dev.abarmin.balance.app");
```

Classes now should have annotation to be visible to Spring: 

```java
@Component
public class BalanceHandler implements HttpHandler {
    @Autowired
    private BalanceService balanceService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    // other code goes here
}
```