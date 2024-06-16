# Customizing Bean Definitions with Bean Factory Post Processor

In contrast with Bean Post Processor, Bean Factory Post Processor is dealing with Bean Definitions not
Bean instances so that it can configure definitions only. Works in the same way - implement
`BeanFactoryPostProcessor` interface and by having access to the `BeanFactory` perform updates of
definitions. 

Example: 

```java
public class BalanceBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        BeanDefinition serviceDefinition = beanFactory.getBeanDefinition("balanceService");
        serviceDefinition.getPropertyValues().add("helloMessage", "This is a custom message");
    }
}
```