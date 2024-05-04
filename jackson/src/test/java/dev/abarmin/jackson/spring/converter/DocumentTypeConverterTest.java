package dev.abarmin.jackson.spring.converter;

import dev.abarmin.jackson.spring.model.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ContextConfiguration(classes = DocumentTypeConverter.class)
class DocumentTypeConverterTest {
    @Autowired
    JacksonTester<ForTest> json;

    @Test
    void shouldDeserialize() throws Exception {
        String myJson = """
                {
                    "type": "Paper Document"
                }
                """;
        assertThat(json.parse(myJson)).isEqualTo(ForTest.builder()
                .type(DocumentType.PAPER)
                .build());
    }

    @Test
    void shouldSerialize() throws Exception {
        assertThat(json.write(ForTest.builder()
                .type(DocumentType.DIGITAL)
                .build())).hasJsonPathValue("$.type", "Digital Document");
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class ForTest {
        private DocumentType type;
    }
}