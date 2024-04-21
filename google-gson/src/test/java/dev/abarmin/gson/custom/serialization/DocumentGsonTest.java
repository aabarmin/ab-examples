package dev.abarmin.gson.custom.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DocumentGsonTest {
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
                    "title": "Digital Document",
                    "path": "~/home/document.txt",
                    "type": "digital"
                  },
                  {
                    "title": "Paper Document",
                    "pages": 32,
                    "type": "paper"
                  }
                ]
                """;

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeHierarchyAdapter(Document.class, new DocumentTypeAdapter())
            .create();

    @Test
    void serialize_shouldIncludeDocumentType() {
        final String serialized = gson.toJson(DOCS);
        assertThat(serialized).isEqualToIgnoringWhitespace(JSON);
    }

    @Test
    void deserialize_shouldUserTypeInformation() {
        final TypeToken<List<Document>> typeToken = new TypeToken<>() {};
        final List<Document> documents = gson.fromJson(JSON, typeToken);

        assertThat(documents).containsAll(DOCS);
    }
}
