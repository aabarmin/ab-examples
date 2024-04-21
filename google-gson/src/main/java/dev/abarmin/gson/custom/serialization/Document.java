package dev.abarmin.gson.custom.serialization;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
abstract sealed class Document permits DigitalDocument, PaperDocument {
    private String title;
}
