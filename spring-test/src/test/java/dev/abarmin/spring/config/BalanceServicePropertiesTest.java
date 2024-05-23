package dev.abarmin.spring.config;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class BalanceServicePropertiesTest {
    @Nested
    @EnableConfigurationProperties(BalanceServiceProperties.class)
    @TestPropertySource(properties = {
            "integration.balance-service.endpoint.base-url=http://test"
    })
    class WithPropertySource {
        @Autowired
        BalanceServiceProperties properties;

        @Test
        void contextLoaded_propertiesSet() {
            assertThat(properties).isNotNull();
            assertThat(properties.getEndpoint().getBaseUrl()).isEqualTo("http://test");
        }
    }

    @Nested
    @EnableConfigurationProperties(BalanceServiceProperties.class)
    class WithDynamicPropertySource {
        @DynamicPropertySource
        static void loadProperties(DynamicPropertyRegistry registry) {
            registry.add(
                    "integration.balance-service.endpoint.base-url",
                    () -> "http://test");
        }

        @Autowired
        BalanceServiceProperties properties;

        @Test
        void contextLoaded_propertiesSet() {
            assertThat(properties).isNotNull();
            assertThat(properties.getEndpoint().getBaseUrl()).isEqualTo("http://test");
        }
    }
}