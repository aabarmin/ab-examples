package dev.abarmin.spring.controller;

import dev.abarmin.spring.model.Money;
import dev.abarmin.spring.model.Transaction;
import dev.abarmin.spring.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<GetTransactionsResponse> getTransactions() {
        return ResponseEntity.ok(new GetTransactionsResponse(List.of()));
    }

    @PostMapping
    public ResponseEntity<Void> createTransaction(@RequestBody CreateTransactionRequest request) {
        Transaction createdTransaction = transactionService.createTransaction(request.toTransaction());
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .build(createdTransaction.id()))
                .build();
    }
}

record GetTransactionsResponse(Collection<Transaction> transactions) {
}

record CreateTransactionRequest(long from,
                                long to,
                                Money amount) {

    Transaction toTransaction() {
        return new Transaction(null, from, to, amount);
    }
}
