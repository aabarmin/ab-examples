package dev.abarmin.balance.app.config;

import dev.abarmin.balance.app.controller.BalanceHandler;
import dev.abarmin.balance.app.service.BalanceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;

public class BalanceConfiguration {
    @Bean
    public BalanceService balanceService() {
        return new BalanceService();
    }

    @Bean
    public BalanceHandler balanceHandler(ObjectMapper objectMapper,
                                         BalanceService balanceService) {
        final BalanceHandler handler = new BalanceHandler();
        handler.setBalanceService(balanceService);
        handler.setObjectMapper(objectMapper);
        return handler;
    }
}
