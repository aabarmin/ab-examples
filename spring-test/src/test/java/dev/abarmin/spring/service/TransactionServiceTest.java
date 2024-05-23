package dev.abarmin.spring.service;

import dev.abarmin.spring.client.BalanceServiceClient;
import dev.abarmin.spring.converter.MoneyConverterImpl;
import dev.abarmin.spring.converter.TransactionConverterImpl;
import dev.abarmin.spring.entity.TransactionEntity;
import dev.abarmin.spring.model.Money;
import dev.abarmin.spring.model.Transaction;
import dev.abarmin.spring.model.TransactionStatus;
import dev.abarmin.spring.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = {
        TransactionService.class,
        MoneyConverterImpl.class,
        TransactionConverterImpl.class
})
@MockBean(BalanceServiceClient.class)
class TransactionServiceTest {
    @Autowired
    TransactionService transactionService;

    @MockBean
    TransactionRepository transactionRepository;

    @Test
    void createTransaction_savesProvidedTransaction() {
        when(transactionRepository.save(any(TransactionEntity.class))).thenAnswer(inv -> {
            TransactionEntity entity = inv.getArgument(0, TransactionEntity.class);
            entity.setId(42L);
            return entity;
        });

        Transaction originalTransaction = new Transaction(null, 1L, 2L, Money.of(10, "GBP"), TransactionStatus.AUTHORISED);
        Transaction createdTransaction = transactionService.createTransaction(originalTransaction);

        assertThat(createdTransaction.id()).isNotNull();
    }
}