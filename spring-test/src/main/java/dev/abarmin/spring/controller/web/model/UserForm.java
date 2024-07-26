package dev.abarmin.spring.controller.web.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class UserForm {
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @Size(min = 1)
    private Collection<String> authorities = new ArrayList<>();
}
