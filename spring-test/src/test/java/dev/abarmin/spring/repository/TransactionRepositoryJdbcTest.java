package dev.abarmin.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@PerformDatabaseMigration
@Sql(statements = "delete from TRANSACTIONS", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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

    @Test
    @Sql(statements = {
            """
                    insert into TRANSACTIONS (account_from, account_to, amount_currency, amount_value, transaction_status) 
                    values 
                    (1, 2, 'USD', 100, 'AUTHORIZED'), 
                    (2, 2, 'USD', 100, 'AUTHORIZED'),
                    (3, 2, 'USD', 100, 'AUTHORIZED');
                                                                """
    })
    void insertTestRecordFromAnnotation() {
        int recordsCount = JdbcTestUtils.deleteFromTables(jdbcTemplate, "TRANSACTIONS");
        assertThat(recordsCount)
                .withFailMessage("Test record should be inserted")
                .isEqualTo(3);
    }

    @Test
    void ordinaryTestShouldNotHaveRecords() {
        assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "TRANSACTIONS"))
                .withFailMessage("Test record should not be inserted")
                .isEqualTo(0);
    }
}
