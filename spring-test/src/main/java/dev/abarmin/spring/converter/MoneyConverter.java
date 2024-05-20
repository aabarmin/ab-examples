package dev.abarmin.spring.converter;

import dev.abarmin.spring.entity.MoneyEntity;
import dev.abarmin.spring.model.Money;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MoneyConverter {
    Money fromEntity(MoneyEntity entity);

    MoneyEntity toEntity(Money money);
}
