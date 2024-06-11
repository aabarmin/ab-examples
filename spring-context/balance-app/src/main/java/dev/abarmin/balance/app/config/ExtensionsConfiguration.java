package dev.abarmin.balance.app.config;

import dev.abarmin.balance.app.extension.bdrpp.CustomBalanceProcessorsRegistrar;
import dev.abarmin.balance.app.extension.bfpp.BalanceBeanFactoryPostProcessor;
import dev.abarmin.balance.app.extension.bpp.HttpLoggingBeanPostProcessor;
import dev.abarmin.balance.app.extension.bpp.LoggingBeanPostProcessor;
import org.springframework.context.annotation.Bean;

public class ExtensionsConfiguration {
    @Bean
    public static LoggingBeanPostProcessor beanPostProcessor() {
        return new LoggingBeanPostProcessor();
    }

    @Bean
    public static HttpLoggingBeanPostProcessor loggingBeanPostProcessor() {
        return new HttpLoggingBeanPostProcessor();
    }

    @Bean
    public static BalanceBeanFactoryPostProcessor balanceBeanFactoryPostProcessor() {
        return new BalanceBeanFactoryPostProcessor();
    }

    @Bean
    public static CustomBalanceProcessorsRegistrar balanceProcessorsRegistrar() throws Exception {
        return new CustomBalanceProcessorsRegistrar();
    }
}
