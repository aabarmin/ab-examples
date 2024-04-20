package dev.abarmin.wt.examples.gson;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public final class PaperDocument extends Document {
    private int pages;
}
