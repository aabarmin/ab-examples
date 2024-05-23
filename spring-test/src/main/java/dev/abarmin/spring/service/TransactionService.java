package dev.abarmin.spring.service;

import dev.abarmin.spring.client.BalanceServiceClient;
import dev.abarmin.spring.converter.TransactionConverter;
import dev.abarmin.spring.entity.TransactionEntity;
import dev.abarmin.spring.model.Transaction;
import dev.abarmin.spring.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final TransactionConverter converter;
    private final BalanceServiceClient balanceServiceClient;

    public Transaction createTransaction(Transaction transaction) {
        TransactionEntity transactionEntity = converter.toEntity(transaction);
        TransactionEntity savedTransaction = repository.save(transactionEntity);
        balanceServiceClient.reserve(savedTransaction.getId(), transaction.amount());
        return converter.fromEntity(savedTransaction);
    }

    public Collection<Transaction> findAll() {
        return repository.findAll()
                .stream()
                .map(converter::fromEntity)
                .toList();
    }
}
