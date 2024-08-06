# Integration between Spring Security and MockMvc

To use integration between Spring Security and MockMvc it is necessary to add `SecurityMockMvcConfigurer` to
the `MockMvc` configuration and this can be done by using:

```java
class TransactionControllerTest {
    MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .alwaysDo(print())
                .build();
    }
}
```

There are two options to send authorised request now. The first one is to put `@WithMockUser` annotation on top of the
test method: 

```java
    @Test
    @WithMockUser(authorities = "admin")
    void add_whenAdminAuthority_thenShowForm() {
        mockMvc.perform(
                get("/web/users/add")
                        .with(csrf())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("users/edit"));
    }
```

Another option is to add user details to the request by using `with`:

```java
    @Test
    void save_whenAdminAuthority_thenSave() throws Exception {
        mockMvc.perform(
                        post("/web/users")
                                .param("username", "Test User")
                                .param("password", "Test Password")
                                .with(user("user").authorities(adminAuthority()))
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/web/users"));

        verify(userManager).createUser(any(UserDetails.class));
    }
```