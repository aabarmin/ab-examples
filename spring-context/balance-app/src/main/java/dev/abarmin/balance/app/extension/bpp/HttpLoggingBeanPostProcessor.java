package dev.abarmin.balance.app.extension.bpp;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpLoggingBeanPostProcessor implements BeanPostProcessor {
    private final Map<String, HttpHandler> handlers = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof HttpHandler handler) {
            handlers.put(beanName, handler);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (handlers.containsKey(beanName)) {
            return new HttpLoggingWrapper(handlers.get(beanName));
        }
        return bean;
    }

    @Slf4j
    @RequiredArgsConstructor
    static class HttpLoggingWrapper implements HttpHandler {
        private final HttpHandler delegate;

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            log.info(
                    "Request of type {} to the endpoint {}",
                    exchange.getRequestMethod(),
                    exchange.getRequestURI().toString());
            delegate.handle(exchange);
        }
    }
}
