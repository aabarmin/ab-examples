package dev.abarmin.jackson.spring.converter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@JsonComponent
public class LocalDateConverter {
    public static class Deserializer extends JsonDeserializer<LocalDate> {
        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            final String dateAsString = ctxt.readValue(p, String.class);
            return LocalDate.parse(dateAsString, formatter);
        }
    }
}
