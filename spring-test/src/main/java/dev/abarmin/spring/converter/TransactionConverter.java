package dev.abarmin.spring.converter;

import dev.abarmin.spring.entity.TransactionEntity;
import dev.abarmin.spring.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {MoneyConverter.class, StepConverter.class})
public interface TransactionConverter {
    Transaction fromEntity(TransactionEntity entity);
}
