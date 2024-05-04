package dev.abarmin.jackson.spring.converter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import dev.abarmin.jackson.spring.model.DocumentType;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class DocumentTypeConverter {
    public static class Deserializer extends JsonDeserializer<DocumentType> {
        @Override
        public DocumentType deserialize(JsonParser p,
                                        DeserializationContext ctxt) throws IOException, JacksonException {

            final String stringValue = ctxt.readValue(p, String.class);
            return DocumentType.byValue(stringValue);
        }
    }

    public static class Serializer extends JsonSerializer<DocumentType> {
        @Override
        public void serialize(DocumentType value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.getValue());
        }
    }
}
