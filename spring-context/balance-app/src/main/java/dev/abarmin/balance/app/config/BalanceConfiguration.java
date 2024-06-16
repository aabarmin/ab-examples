package dev.abarmin.balance.app.config;

import dev.abarmin.balance.app.handler.BalanceHandler;
import dev.abarmin.balance.app.service.processor.BalanceProcessor;
import dev.abarmin.balance.app.service.BalanceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.abarmin.balance.app.service.processor.LoggingBalanceProcessor;
import org.springframework.context.annotation.Bean;

public class BalanceConfiguration {
    @Bean
    public BalanceService balanceService() {
        return new BalanceService();
    }

    @Bean
    public BalanceProcessor loggingBalanceProcessor() {
        return new LoggingBalanceProcessor();
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
