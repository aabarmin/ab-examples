# Spring Test Containers support

Test Containers are gracefully supported by Spring Test and to use it please add the following dependencies
to `build.gradle`.

```groovy
testImplementation 'org.springframework.boot:spring-boot-testcontainers'
testImplementation 'org.testcontainers:junit-jupiter'
testImplementation 'org.testcontainers:postgresql'
```

The first dependency adds integration between Test Containers and the Spring application, the second one
adds `@Testcontainers` extension, and the third one is just a test container for PostgreSQL.

Next it is necessary to run the container and propagate ports and hosts to the application context that can be done by
using the `@DynamicPropertySource` annotation: 

```java
@DataJpaTest
@Testcontainers
class TransactionRepositoryJpaContainerTest {
    @Container
    static PostgreSQLContainer DB = new PostgreSQLContainer();

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> DB.getJdbcUrl());
        registry.add("spring.datasource.username", () -> DB.getUsername());
        registry.add("spring.datasource.password", () -> DB.getPassword());
        registry.add("spring.test.database.replace", () -> false);
    }
    
    // tests go here
}
```