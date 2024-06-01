package com.account.balance;

import com.account.balance.config.BalanceConfiguration;
import com.account.balance.config.ObjectMapperConfiguration;
import com.account.balance.controller.BalanceHandler;
import com.account.balance.service.BalanceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.InetSocketAddress;

public class BalanceApplication {
    public static void main(String[] args) throws Exception {
        final ApplicationContext context = getXmlContext();

        final HttpHandler handler = context.getBean(HttpHandler.class);

        final HttpServer server = HttpServer.create(new InetSocketAddress(9090), 0);
        server.createContext("/", handler);
        server.start();
    }

    private static ApplicationContext getXmlContext() {
        return new ClassPathXmlApplicationContext("classpath:/balance-service-context.xml");
    }

    private static ApplicationContext getJavaContext() {
        return new AnnotationConfigApplicationContext(
                BalanceConfiguration.class,
                ObjectMapperConfiguration.class);
    }

    private static ApplicationContext getAnnotationContext() {
        return new AnnotationConfigApplicationContext(
                ObjectMapper.class,
                BalanceHandler.class,
                BalanceService.class
        );
    }

    private static ApplicationContext getContextWithComponentScan() {
        return new AnnotationConfigApplicationContext("com.account.balance");
    }
}
