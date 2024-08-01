package dev.abarmin.spring.controller.rest;

import dev.abarmin.spring.model.AuthorisationRequest;
import dev.abarmin.spring.model.AuthorisationResponse;
import dev.abarmin.spring.model.GetTransactionsResponse;
import dev.abarmin.spring.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<GetTransactionsResponse> getTransactions() {
        return ResponseEntity.ok(new GetTransactionsResponse(transactionService.findAll()));
    }

    @PostMapping
    public ResponseEntity<AuthorisationResponse> authorise(@RequestBody AuthorisationRequest request) {
        final AuthorisationResponse response = transactionService.authorise(request);
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .build(response.transactionId()))
                .build();
    }
}