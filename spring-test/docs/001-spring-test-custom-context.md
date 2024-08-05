# Creating custom application context for tests

To create a tiny config for testing purposes only, use `@ContextConfiguration` annotation with conjunction
with `@SpyBean` and `@MockBean` annotations. Also, don't forget using `@ExtendsWith(SpringExtension.class)` annotation.
A pair of `@ExtendsWith` and `@ContextConfiguration` can be replaced with `@SpringJUnitConfig` meta-annotation. 

```java

@SpringJUnitConfig({
        TransactionService.class,
        MoneyConverterImpl.class,
        TransactionConverterImpl.class,
        StepConverterImpl.class
})
@SpyBean(AuthoriseRequestConverter.class)
@MockBean(TransactionRepository.class)
@MockBean(BalanceClient.class)
class TransactionServiceTest {

}
```