package dev.abarmin.jackson.spring.controller;

import dev.abarmin.jackson.spring.converter.DocumentTypeConverter;
import dev.abarmin.jackson.spring.converter.LocalDateConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {
        DocumentController.class,
        LocalDateConverter.class,
        DocumentTypeConverter.class
})
class DocumentControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldCreate() throws Exception {
        String content = """
                {
                    "created": "10.12.2024",
                    "type": "Paper Document"
                }
                """;
        mockMvc.perform(post("/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated());
    }
}