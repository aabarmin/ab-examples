package dev.abarmin.spring.repository;

import dev.abarmin.spring.entity.MoneyEntity;
import dev.abarmin.spring.entity.TransactionEntity;
import dev.abarmin.spring.model.TransactionStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@PerformDatabaseMigration
class TransactionRepositoryJpaTest {
    @Autowired
    TransactionRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void findById_shouldBeAbleToReadViaRepository() {
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .accountFrom(1L)
                .accountTo(2L)
                .amount(MoneyEntity.builder()
                        .amount(BigDecimal.TEN)
                        .currency("GBP")
                        .build())
                .status(TransactionStatus.AUTHORISED)
                .build();

        TransactionEntity persistedEntity = entityManager.persist(transactionEntity);

        TransactionEntity retrievedEntity = repository.findById(persistedEntity.getId()).orElseThrow();

        assertThat(retrievedEntity)
                .usingRecursiveComparison()
                .isEqualTo(persistedEntity);
    }
}