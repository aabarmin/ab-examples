package dev.abarmin.balance.app;

import dev.abarmin.balance.app.config.BalanceConfiguration;
import dev.abarmin.balance.app.config.ExtensionsConfiguration;
import dev.abarmin.balance.app.config.ObjectMapperConfiguration;
import dev.abarmin.balance.app.handler.BalanceHandler;
import dev.abarmin.balance.app.service.BalanceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import dev.abarmin.balance.app.service.processor.LoggingBalanceProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.InetSocketAddress;

public class BalanceApplication {
    public static void main(String[] args) throws Exception {
        final ApplicationContext context = getJavaContext();

        final HttpHandler handler = context.getBean(HttpHandler.class);

        final HttpServer server = HttpServer.create(new InetSocketAddress(9090), 0);
        server.createContext("/", handler);
        server.start();
    }

    static ApplicationContext getXmlContext() {
        return new ClassPathXmlApplicationContext("classpath:/balance-service-context.xml");
    }

    static ApplicationContext getJavaContext() {
        return new AnnotationConfigApplicationContext(
                ExtensionsConfiguration.class,
                BalanceConfiguration.class,
                ObjectMapperConfiguration.class);
    }

    static ApplicationContext getAnnotationContext() {
        return new AnnotationConfigApplicationContext(
                ObjectMapper.class,
                BalanceHandler.class,
                BalanceService.class,
                LoggingBalanceProcessor.class
        );
    }

    static ApplicationContext getContextWithComponentScan() {
        return new AnnotationConfigApplicationContext("dev.abarmin.balance.app");
    }
}
