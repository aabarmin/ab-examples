package dev.abarmin.spring.repository;

import dev.abarmin.spring.entity.TransactionEntity;
import dev.abarmin.spring.entity.TransactionEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

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