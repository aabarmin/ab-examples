package dev.abarmin.spring.repository;

import dev.abarmin.spring.entity.TransactionEntity;
import dev.abarmin.spring.entity.TransactionEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@PerformDatabaseMigration
class TransactionRepositoryJpaContainerTest {
    @Container
    static PostgreSQLContainer DB = new PostgreSQLContainer();

    @DynamicPropertySource
    static void propertySource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> DB.getJdbcUrl());
    }

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    void checkRecordsSaved() {
        TransactionEntity transaction = TransactionEntityFactory.createAuthorised(1, 2, 10);
        final Long transactionId = entityManager.persistAndGetId(transaction, Long.class);

        final TransactionEntity retrievedTransaction = transactionRepository.findById(transactionId).orElseThrow();
        assertThat(retrievedTransaction)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(transaction);
    }
}