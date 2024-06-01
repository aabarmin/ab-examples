package com.account.balance.controller;

import com.account.balance.service.BalanceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dev.abarmin.spring.model.Money;
import lombok.Setter;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Setter
public class BalanceHandler implements HttpHandler {
    private final Pattern balancePattern = Pattern.compile("/balance/(\\d+)/reserve");

    private BalanceService balanceService;
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        final String requestMethod = exchange.getRequestMethod();
        // check method
        if (!requestMethod.equals("POST")) {
            exchange.sendResponseHeaders(405, 0);
            exchange.getResponseBody().close();
            return;
        }

        // check endpoint
        final String requestURI = exchange.getRequestURI().toString();
        final Matcher matcher = balancePattern.matcher(requestURI);
        if (!matcher.matches()) {
            exchange.sendResponseHeaders(404, 0);
            exchange.getResponseBody().close();
            return;
        }

        // extract values
        final OutputStream response = exchange.getResponseBody();
        try {
            final long accountId = Long.parseLong(matcher.group(1));
            final Money amount = objectMapper.readerFor(Money.class).<Money>readValue(exchange.getRequestBody());

            final boolean reserved = balanceService.reserve(accountId, amount);

            exchange.sendResponseHeaders(200, 0);
            response.write(String.valueOf(reserved).getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            exchange.sendResponseHeaders(500, 0);
            final String exception = ExceptionUtils.getStackTrace(e);
            response.write(exception.getBytes(StandardCharsets.UTF_8));
        } finally {
            response.close();
        }
    }
}
