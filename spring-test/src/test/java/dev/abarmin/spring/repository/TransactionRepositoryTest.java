package dev.abarmin.spring.repository;

import dev.abarmin.spring.entity.MoneyEntity;
import dev.abarmin.spring.entity.TransactionEntity;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.sql.DataSource;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TransactionRepositoryTest {
    @Autowired
    TransactionRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    DataSource dataSource;

    @BeforeEach
    void setUp() {
        Flyway.configure()
                .dataSource(dataSource)
                .load()
                .migrate();
    }

    @Test
    void findById_shouldBeAbleToReadViaRepository() {
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .accountFrom(1L)
                .accountTo(2L)
                .amount(MoneyEntity.builder()
                        .amount(BigDecimal.TEN)
                        .currency("GBP")
                        .build())
                .build();

        TransactionEntity persistedEntity = entityManager.persist(transactionEntity);

        TransactionEntity retrievedEntity = repository.findById(persistedEntity.getId()).orElseThrow();

        assertThat(retrievedEntity)
                .usingRecursiveComparison()
                .isEqualTo(persistedEntity);
    }
}