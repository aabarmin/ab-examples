package dev.abarmin.jackson.spring.converter;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ContextConfiguration(classes = LocalDateConverter.class)
class LocalDateConverterTest {
    @Autowired
    JacksonTester<TestObject> json;

    @Test
    void converter_canReadDates() throws Exception {
        String content = """
                {
                    "date": "10.12.2024"
                }
                """;
        assertThat(json.parse(content))
                .hasFieldOrPropertyWithValue("date", LocalDate.of(2024, Month.DECEMBER, 10));
    }

    @Data
    static class TestObject {
        LocalDate date;
    }
}