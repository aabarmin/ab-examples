package dev.abarmin.spring.service;

import dev.abarmin.balance.client.BalanceClient;
import dev.abarmin.balance.common.model.Money;
import dev.abarmin.spring.converter.AuthoriseRequestConverter;
import dev.abarmin.spring.converter.MoneyConverterImpl;
import dev.abarmin.spring.converter.StepConverterImpl;
import dev.abarmin.spring.converter.TransactionConverterImpl;
import dev.abarmin.spring.entity.TransactionEntity;
import dev.abarmin.spring.model.AuthorisationRequest;
import dev.abarmin.spring.model.AuthorisationResponse;
import dev.abarmin.spring.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = {
        TransactionService.class,
        MoneyConverterImpl.class,
        StepConverterImpl.class,
        TransactionConverterImpl.class,
        AuthoriseRequestConverter.class
})
@MockBean(BalanceClient.class)
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

        final AuthorisationRequest request = new AuthorisationRequest(1l, 2l, Money.of(10, "GBP"));
        final AuthorisationResponse response = transactionService.authorise(request);

        assertThat(response.transactionId()).isNotNull();
    }
}