# Testing Spring Data JPA repositories

Testing of JPA repositories is more complicated than testing of [plan JDBC repositories](003-spring-test-jdbc.md) as it
requires more components to be configured, a lot of Spring's magic to be applied. Luckily, Spring has another testing
slice that allows to get all the configurations ready, just use `@DataJpaTest`.

Actually, other `@Data--Test` annotations are available, for example, `@DataMongoTest` that does the same but for Mongo
repos.

The test component that is added here is `TestEntityManager` that allow to prepare proper DB state before testing
Spring's repositories. 

```java
@DataJpaTest
class TransactionRepositoryJpaTest {
    @Autowired
    TransactionRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void findById_shouldBeAbleToReadViaRepository() {
        TransactionEntity transactionEntity = TransactionEntityFactory.createAuthorised(1, 2, 10);

        TransactionEntity persistedEntity = entityManager.persist(transactionEntity);

        TransactionEntity retrievedEntity = repository.findById(persistedEntity.getId()).orElseThrow();

        assertThat(retrievedEntity)
                .usingRecursiveComparison()
                .isEqualTo(persistedEntity);
    }
}
```