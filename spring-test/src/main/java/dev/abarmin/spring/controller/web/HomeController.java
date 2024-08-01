package dev.abarmin.spring.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String homeController() {
        return "redirect:/web/transactions";
    }
}
