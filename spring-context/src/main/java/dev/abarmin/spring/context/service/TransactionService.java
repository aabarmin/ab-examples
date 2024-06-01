package dev.abarmin.spring.context.service;

import dev.abarmin.spring.context.model.AuthoriseRequest;
import dev.abarmin.spring.context.model.AuthoriseResponse;
import dev.abarmin.spring.context.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    public AuthoriseResponse authorise(AuthoriseRequest request) {
        throw new UnsupportedOperationException();
    }
}
