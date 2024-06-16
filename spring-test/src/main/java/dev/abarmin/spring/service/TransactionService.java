package dev.abarmin.spring.service;

import dev.abarmin.balance.client.BalanceClient;
import dev.abarmin.spring.converter.AuthoriseRequestConverter;
import dev.abarmin.spring.converter.TransactionConverter;
import dev.abarmin.spring.entity.TransactionEntity;
import dev.abarmin.spring.model.AuthorisationRequest;
import dev.abarmin.spring.model.AuthorisationResponse;
import dev.abarmin.spring.model.Transaction;
import dev.abarmin.spring.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final AuthoriseRequestConverter requestConverter;
    private final TransactionRepository repository;
    private final TransactionConverter converter;
    private final BalanceClient balanceClient;

    @Transactional
    public AuthorisationResponse authorise(AuthorisationRequest request) {
        balanceClient.reserve(request.from(), request.amount());
        final TransactionEntity transaction = requestConverter.toEntity(request);
        final TransactionEntity savedTransaction = repository.save(transaction);
        return new AuthorisationResponse(savedTransaction.getId(), request.amount());
    }

    @Transactional
    public Collection<Transaction> findAll() {
        return repository.findAll()
                .stream()
                .map(converter::fromEntity)
                .toList();
    }
}
