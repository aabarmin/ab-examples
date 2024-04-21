package dev.abarmin.jackson.custom.serialization;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
abstract sealed class Vehicle permits Car, Truck {
    private String model;
}
