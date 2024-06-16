package dev.abarmin.spring.converter;

import dev.abarmin.balance.common.model.Money;
import dev.abarmin.spring.entity.MoneyEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MoneyConverter {
    Money fromEntity(MoneyEntity entity);

    MoneyEntity toEntity(Money money);
}
