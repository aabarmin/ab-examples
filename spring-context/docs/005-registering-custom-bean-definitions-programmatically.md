# Registering custom bean definitions with Bean Definition Registry Post Processor

Bean Definition Registry Post Processor is executed even earlier than Bean Factory Post Processor and
allows to register new bean definitions. For example: 

```java
public class CustomBalanceProcessorsRegistrar implements BeanDefinitionRegistryPostProcessor {
    private final CustomBalanceConfig customBalanceConfig;

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
}
```