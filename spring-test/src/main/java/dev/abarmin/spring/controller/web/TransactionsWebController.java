package dev.abarmin.spring.controller.web;

import dev.abarmin.spring.converter.TransactionConverter;
import dev.abarmin.spring.model.Transaction;
import dev.abarmin.spring.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web/transactions")
public class TransactionsWebController {
    private final TransactionRepository repository;
    private final TransactionConverter converter;

    @GetMapping
    public String viewAll(Model model) {
        List<Transaction> transactions = repository.findAll().stream()
                .map(converter::fromEntity)
                .toList();

        model.addAttribute("transactions", transactions);

        return "transactions/index";
    }
}
