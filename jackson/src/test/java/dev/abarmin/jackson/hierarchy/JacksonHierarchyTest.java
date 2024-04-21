package dev.abarmin.jackson.hierarchy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JacksonHierarchyTest {
    private static final Collection<Document> DOCS = List.of(
            DigitalDocument.builder()
                    .title("Digital Document")
                    .path("~/home/document.txt")
                    .build(),
            PaperDocument.builder()
                    .title("Paper Document")
                    .pages(32)
                    .build()
    );
    private static final String JSON = """
            [ 
            {
              "type" : "digital",
              "title" : "Digital Document",
              "path" : "~/home/document.txt"
            }, 
            {
              "type" : "paper",
              "title" : "Paper Document",
              "pages" : 32
            } 
            ]
                        """;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules()
            .enable(SerializationFeature.INDENT_OUTPUT);

    @Test
    void serialize_shouldIncludeDocumentType() throws Exception {
        final String serialized = objectMapper
                .writerFor(new TypeReference<List<Document>>() {
                })
                .writeValueAsString(DOCS);

        assertThat(serialized).isEqualToIgnoringWhitespace(JSON);
    }

    @Test
    void deserialize_shouldConsiderDocumentTypes() throws Exception {
        final List<Document> documents = objectMapper
                .readerForListOf(Document.class)
                .readValue(JSON);
        assertThat(documents).isEqualTo(DOCS);
    }
}
