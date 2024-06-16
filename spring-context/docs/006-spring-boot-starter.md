# How to create a Spring Boot Starter

Spring Boot Starter is nothing but an instruction for Spring Framework to import additional classes into
the application context. Next, these components might be Spring configuration classes that define
component scan configuration or other beans. 

To instruct Spring Framework to load an additional bean to the application context, create a file
`META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` with a list of classes
to be imported. As these classes might be configurations it is better to put `@AutoConfiguration` annotations
instead as it allows to control order in which these classes are processed. 

Example: 

```java
@AutoConfiguration
@PropertySource("classpath:/balance-client-defaults.properties")
@EnableConfigurationProperties(BalanceRestProperties.class)
@ConditionalOnProperty(
        prefix = "integration.balance-service",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true)
public class BalanceClientAutoconfiguration {
    @Bean
    @ConditionalOnMissingBean
    public BalanceClient balanceRestClient(RestClient.Builder builder,
                                           BalanceRestProperties properties) {
        return new BalanceRestClient(builder, properties);
    }
}
```