package dev.abarmin.wt.examples.jackson;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
abstract sealed class Vehicle permits Car, Truck {
    private String model;
}
