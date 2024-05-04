package dev.abarmin.jackson.spring.controller;

import dev.abarmin.jackson.spring.model.Document;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocumentController {
    @PostMapping("/documents")
    @ResponseStatus(HttpStatus.CREATED)
    public String createDocument(@RequestBody Document document) {
        return "Created!";
    }
}
