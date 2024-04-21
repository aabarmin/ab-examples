package dev.abarmin.wt.examples.gson;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
abstract sealed class Document permits DigitalDocument, PaperDocument {
    private String title;
}
