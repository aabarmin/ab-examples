package dev.abarmin.jackson.custom.serialization;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
final class Truck extends Vehicle {
    private int capacity;
}
