package dev.abarmin.spring.context.controller;

import dev.abarmin.spring.context.model.AuthoriseRequest;
import dev.abarmin.spring.context.model.AuthoriseResponse;
import dev.abarmin.spring.context.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/authorise")
    public ResponseEntity<AuthoriseResponse> authorise(@RequestBody AuthoriseRequest request) {
        final AuthoriseResponse response = transactionService.authorise(request);
        return ResponseEntity.ok(response);
    }
}
