package dev.abarmin.spring.converter;

import dev.abarmin.spring.entity.StepEntity;
import dev.abarmin.spring.entity.TransactionEntity;
import dev.abarmin.spring.model.AuthorisationRequest;
import dev.abarmin.spring.model.TransactionStatus;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthoriseRequestConverter {
    private final MoneyConverter moneyConverter ;
    public TransactionEntity toEntity(@NonNull AuthorisationRequest request) {
        final TransactionEntity transactionEntity = TransactionEntity.builder()
                .accountFrom(request.from())
                .accountTo(request.to())
                .amount(moneyConverter.toEntity(request.amount()))
                .status(TransactionStatus.AUTHORISED)
                .build();

        final StepEntity step = StepEntity.builder()
                .transaction(transactionEntity)
                .amount(moneyConverter.toEntity(request.amount()))
                .build();

        transactionEntity.getSteps().add(step);

        return transactionEntity;
    }
}
