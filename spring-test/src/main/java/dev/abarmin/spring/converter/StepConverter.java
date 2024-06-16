package dev.abarmin.spring.converter;

import dev.abarmin.spring.entity.StepEntity;
import dev.abarmin.spring.model.Step;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StepConverter {
    Step fromEntity(StepEntity entity);
}
