# Testing REST controllers

REST controllers might be tested as normal Spring beans by using `@SpringJUnitConfig` meta annotation but this approach
doesn't allow to test the full stack of components that are in the middle between REST endpoint and the client, for
example, the serialization in REST controllers, response codes, controller advices, etc. can't be tested.

Here Spring suggests testing application in slices, and REST slice can be tested by using `@WebMvcTest` configuration (
there is another `@WebFluxTest` for reactive applications). The core of this testing is `MockMvc` component that
simulates the client that sends requests and receives responses.

`MockMvc` component can be created manually by using `MockMvcBuilders` and it is useful for cases when integration with
Spring Security is required. Also, it is configured automatically by using `@AutoConfigureMockMvc` which is a part
of `@WebMvcTest` so can be just autowired. There is no preferred way, in case if the test is complicated, it is better
to create the `MockMvc` component by using the builder. 

```java
@WebMvcTest({
        TransactionController.class,
        TransactionConverterImpl.class,
        MoneyConverterImpl.class,
        StepConverterImpl.class,
        SecurityConfiguration.class
})
@MockBean(UserDetailsManager.class)
class TransactionControllerTest {
    @MockBean
    TransactionService transactionService;
    
    MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .alwaysDo(print())
                .build();
    }

    @Test
    void getTransactions_shouldReturnList() {
        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.transactions").isArray());

        verify(transactionService).findAll();
        verifyNoMoreInteractions(transactionService);
    }
}
```