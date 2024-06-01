package dev.abarmin.spring.config;

import dev.abarmin.spring.model.AuthoriseRequest;
import dev.abarmin.spring.model.Money;
import dev.abarmin.spring.repository.AuthRequestRepository;
import dev.abarmin.spring.service.AuthorisationRequestConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.jdbc.JdbcPollingChannelAdapter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.sql.DataSource;
import java.time.Duration;

@Slf4j
@Configuration
@EnableIntegration
public class IntegrationConfiguration {
    public MessageSource<Object> requestMessageSource(DataSource dataSource) {
        final JdbcPollingChannelAdapter channelAdapter = new JdbcPollingChannelAdapter(dataSource, "select * from auth_requests");
        channelAdapter.setRowMapper((rs, row) -> new AuthoriseRequest(
                rs.getInt("from_id"),
                rs.getInt("to_id"),
                new Money(
                        rs.getBigDecimal("amount_value"),
                        rs.getString("amount_currency")
                )
        ));
        channelAdapter.setMaxRows(30);
        return channelAdapter;
    }

    @Bean
    public MessageChannel authRequestMessageChannel() {
        return new QueueChannel(10);
    }

    @Bean
    public MessageHandler authRequestMessageHandler(AuthRequestRepository repository) {
        return message -> {
            log.info("Message of type {} received", message.getPayload().getClass());
            final AuthoriseRequest request = (AuthoriseRequest) message.getPayload();
            repository.save(request);
        };
    }

    @Bean
    public IntegrationFlow savingFlow(MessageHandler authRequestMessageHandler) {
        return IntegrationFlow
                .from(Http.inboundChannelAdapter("/transaction")
                        .requestMapping(r -> r.methods(HttpMethod.POST))
                        .requestPayloadType(AuthoriseRequest.class)
                        .extractReplyPayload(false))
                .handle(authRequestMessageHandler)
                .get();
    }

    @Bean
    public IntegrationFlow processingFlow(DataSource dataSource,
                                          AuthorisationRequestConsumer consumer) {
        return IntegrationFlow
                .from(requestMessageSource(dataSource), s ->
                        s.poller(Pollers.fixedDelay(Duration.ofSeconds(1))))
                .handle(consumer, "consume")
                .get();
    }
}
