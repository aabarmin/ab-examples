package dev.abarmin.jackson.spring.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import dev.abarmin.jackson.spring.model.DocumentType;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class DocumentTypeConverter {
    public static class Deserializer extends JsonDeserializer<DocumentType> {
        @Override
        public DocumentType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            final String stringValue = ctxt.readValue(p, String.class);
            return DocumentType.byValue(stringValue);
        }
    }
}
