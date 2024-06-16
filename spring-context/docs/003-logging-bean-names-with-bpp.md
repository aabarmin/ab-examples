# Logging bean names with Spring Bean Post Processor

Spring Bean Post Processors allow to get access to beans after they're created by Spring Framework but
before they became available for clients to use. The most well-known Bean Post Processor is 
`AutowiredAnnotationBeanPostProcessor` which is responsible for injecting dependencies to fields annotated
with `@Autowired` annotation. 

To create your own Bean Post Processor implement `BeanPostProcessor` interface and override two methods -
`postProcessBeforeInitialization` and `postProcessAfterInitialization`. In the first method provided beans
are already created but methods annotated with `@PostConsturct` aren't called yet so beans might not be
fully initialised yet. Beans available in the second method might be wrapped with proxies already. 

Logging bean names with Bean Post Processor: 

```java
public class LoggingBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.info("[Before] Processing bean with name {}", beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("[After] Processing bean with name {}", beanName);
        return bean;
    }
}
```

Bean Post Processor as any other bean should be discovered. 