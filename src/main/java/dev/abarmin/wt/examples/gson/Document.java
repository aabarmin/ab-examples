package dev.abarmin.wt.examples.gson;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract sealed class Document permits DigitalDocument, PaperDocument {
    private String title;
}
