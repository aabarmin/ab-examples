package dev.abarmin.balance.app.extension.bdrpp;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.abarmin.balance.app.service.processor.GreetingBalanceProcessor;
import dev.abarmin.balance.common.model.Money;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Currency;

public class CustomBalanceProcessorsRegistrar implements BeanDefinitionRegistryPostProcessor {
    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    private final CustomBalanceConfig customBalanceConfig;

    public CustomBalanceProcessorsRegistrar() throws Exception {
        InputStream resourceStream = new ClassPathResource("/balance-processors.json").getInputStream();
        customBalanceConfig = objectMapper.readValue(resourceStream, CustomBalanceConfig.class);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        for (ConfigurationItem config : customBalanceConfig.processors()) {
            BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(GreetingBalanceProcessor.class)
                    .addPropertyValue("limit", new Money(config.limit(), Currency.getInstance("USD")))
                    .addPropertyValue("message", config.message())
                    .getBeanDefinition();

            registry.registerBeanDefinition(config.name(), beanDefinition);
        }
    }

    record CustomBalanceConfig(Collection<ConfigurationItem> processors) {}
    record ConfigurationItem(
            BigDecimal limit,
            String message,
            String name) {}
}
