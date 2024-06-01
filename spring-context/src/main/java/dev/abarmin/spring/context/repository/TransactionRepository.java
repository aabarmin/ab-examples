package dev.abarmin.spring.context.repository;

import dev.abarmin.spring.context.domain.Transaction;
import org.springframework.data.repository.ListCrudRepository;

public interface TransactionRepository extends ListCrudRepository<Transaction, Long> {
}
