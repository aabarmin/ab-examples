# Testing JDBC queries and plain JDBC repositories

In some cases, it is necessary to communicate with the database by using plain SQL and JDBC from the application code.
Ordinary component that is used in this case are `JdbcTemplate` and `JdbcClient`.

To support testing of such kind of communications without using anything else, Spring offers `@JdbcTest` slice that
configures only a data source (including Flyway migrations btw) and `Jdbc`-like components.

Great other annotations to use in conjunction are `@Sql` that allow to execute some SQL scripts as a part of a test's
lifecycle and it is also possible to group `@Sql` expression together by using `@SqlGroup`. 

Useful component available in tests is `JdbcTestUtils` that allows massive operations with

Example of using: 

```java
@JdbcTest
@SqlGroup({
        @Sql(statements = "delete from STEPS", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
        @Sql(statements = "delete from TRANSACTIONS", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class TransactionRepositoryJdbcTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    @Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void insertTestRecordFromScript() {
        int recordsCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "TRANSACTIONS");
        assertThat(recordsCount)
                .withFailMessage("Test record should be inserted")
                .isEqualTo(1);
    }
}
```