package dev.abarmin.spring.service;

import dev.abarmin.spring.model.AuthoriseRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthorisationRequestConsumer {
    @ServiceActivator
    public void consume(AuthoriseRequest request) {
        log.info("Message consumed");
    }
}
