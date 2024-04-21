package dev.abarmin.gson.custom.serialization;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
final class DigitalDocument extends Document {
    private String path;
}
