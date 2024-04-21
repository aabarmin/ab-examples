package dev.abarmin.wt.examples.gson;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
final class DigitalDocument extends Document {
    private String path;
}
