package dev.abarmin.balance.client.rest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class BalanceRestPropertiesTest {
    @Nested
    @EnableConfigurationProperties(BalanceRestProperties.class)
    @TestPropertySource(properties = {
            "integration.balance-service.endpoint.base-url=http://test"
    })
    class WithPropertySource {
        @Autowired
        BalanceRestProperties properties;

        @Test
        void contextLoaded_propertiesSet() {
            Assertions.assertThat(properties).isNotNull();
            Assertions.assertThat(properties.getEndpoint().getBaseUrl()).isEqualTo("http://test");
        }
    }

    @Nested
    @EnableConfigurationProperties(BalanceRestProperties.class)
    class WithDynamicPropertySource {
        @DynamicPropertySource
        static void loadProperties(DynamicPropertyRegistry registry) {
            registry.add(
                    "integration.balance-service.endpoint.base-url",
                    () -> "http://test");
        }

        @Autowired
        BalanceRestProperties properties;

        @Test
        void contextLoaded_propertiesSet() {
            Assertions.assertThat(properties).isNotNull();
            Assertions.assertThat(properties.getEndpoint().getBaseUrl()).isEqualTo("http://test");
        }
    }
}