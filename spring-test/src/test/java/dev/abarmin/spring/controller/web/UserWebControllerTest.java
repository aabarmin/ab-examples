package dev.abarmin.spring.controller.web;

import dev.abarmin.spring.config.SecurityConfiguration;
import dev.abarmin.spring.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest({
        UserWebController.class,
        SecurityConfiguration.class
})
@MockBean(UserDetailsManager.class)
class UserWebControllerTest {
    MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserDetailsManager userManager;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .alwaysDo(print())
                .build();
    }

    @Test
    void index_whenAnonymous_thenNotAuthenticated() throws Exception {
        mockMvc.perform(
                get("/web/users").with(anonymous())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"admin", "user"})
    void index_whenUserAuthority_thenShowList(String authority) throws Exception {
        mockMvc.perform(
                get("/web/users").with(user("user").authorities(withAuthority(authority)))
        )
                .andExpect(status().isOk())
                .andExpect(view().name("users/index"));
    }

    @Test
    void add_whenUserAuthorityTriesAdd_thenNotAuthenticated() throws Exception {
        mockMvc.perform(
                get("/web/users/add")
                        .with(user("user").authorities(userAuthority()))
        )
                .andExpect(status().isForbidden());
    }

    @Test
    void save_whenUserAuthority_thenNotAuthenticated() throws Exception {
        mockMvc.perform(
                        post("/web/users")
                                .param("username", "Test User")
                                .param("password", "Test Password")
                                .with(user("user").authorities(userAuthority()))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "admin")
    void add_whenAdminAuthority_thenShowForm() throws Exception {
        mockMvc.perform(
                get("/web/users/add")
                        .with(csrf())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("users/edit"));
    }

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

    static GrantedAuthority userAuthority() {
        return withAuthority("user");
    }

    static GrantedAuthority adminAuthority() {
        return withAuthority("admin");
    }

    static GrantedAuthority withAuthority(String authority) {
        return new SimpleGrantedAuthority(authority);
    }
}