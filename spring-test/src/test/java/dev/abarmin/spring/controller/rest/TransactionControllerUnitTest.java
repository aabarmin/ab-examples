package dev.abarmin.spring.controller.rest;

import dev.abarmin.balance.common.model.Money;
import dev.abarmin.spring.model.AuthorisationRequest;
import dev.abarmin.spring.model.AuthorisationResponse;
import dev.abarmin.spring.model.GetTransactionsResponse;
import dev.abarmin.spring.model.Transaction;
import dev.abarmin.spring.service.TransactionService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(TransactionController.class)
class TransactionControllerUnitTest {
    @Autowired
    TransactionController transactionController;

    @MockBean
    TransactionService transactionService;

    @Test
    void getTransactions_shouldReturnTransactionsResponse() {
        List<Transaction> transactions = List.of(mock(Transaction.class));
        when(transactionService.findAll()).thenReturn(transactions);

        final ResponseEntity<GetTransactionsResponse> entity =
                transactionController.getTransactions();

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isNotNull();

        final GetTransactionsResponse responseBody = entity.getBody();
        assertThat(responseBody.transactions()).isEqualTo(transactions);

        verify(transactionService).findAll();
    }

    @Test
    @Disabled
    void authorise_responseShouldHaveLocation() {
        final AuthorisationRequest request = new AuthorisationRequest(10l, 10l, Money.of(10, "USD"));
        final AuthorisationResponse authorisationResponse = new AuthorisationResponse(1l, Money.of(10, "USD"));

        when(transactionService.authorise(eq(request))).thenReturn(authorisationResponse);

        final ResponseEntity<AuthorisationResponse> response = transactionController.authorise(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation())
                .isEqualTo("/1");
    }
}