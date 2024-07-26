package dev.abarmin.spring.controller.web;

import dev.abarmin.spring.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web/authorities")
public class AuthorityWebController {
    private final AuthorityRepository authorityRepository;
}
