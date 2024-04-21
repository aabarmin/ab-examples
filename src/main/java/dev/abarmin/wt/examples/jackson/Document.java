package dev.abarmin.wt.examples.jackson;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DigitalDocument.class, name = "digital"),
        @JsonSubTypes.Type(value = PaperDocument.class, name = "paper")
})
@NoArgsConstructor
abstract sealed class Document permits DigitalDocument, PaperDocument {
    private String title;
}
