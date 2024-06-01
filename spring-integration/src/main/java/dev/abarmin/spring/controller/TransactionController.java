package dev.abarmin.spring.controller;

import dev.abarmin.spring.model.AuthoriseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    @Qualifier("authRequestMessageChannel")
    private MessageChannel channel;

    @PostMapping
    public boolean authorise(@RequestBody AuthoriseRequest request) {
        final Message<AuthoriseRequest> message = MessageBuilder
                .withPayload(request)
                .build();
        channel.send(message);
        return true;
    }
}
