package dev.abarmin.wt.examples.gson;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
final class PaperDocument extends Document {
    private int pages;
}
