package dev.abarmin.spring.repository;

import dev.abarmin.spring.entity.TransactionEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface TransactionRepository extends ListCrudRepository<TransactionEntity, Long> {
}
