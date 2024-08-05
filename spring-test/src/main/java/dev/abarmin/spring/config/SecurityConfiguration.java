package dev.abarmin.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {
    @Bean
    @Order(0)
    public SecurityFilterChain h2FilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(AntPathRequestMatcher.antMatcher("/h2-console/**"))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                )
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")))
                .build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain restFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/transactions")
                .authorizeHttpRequests(authorise -> authorise
                        .anyRequest().permitAll())
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorise -> authorise
                        .requestMatchers("/webjars/**").permitAll()

                        // might be replaced with @EnableMethodSecurity and @PreAuthorise
//                        .requestMatchers("/web/users").hasAnyAuthority("admin", "user")
//                        .requestMatchers("/web/users/add").hasAuthority("admin")
//                        .requestMatchers(HttpMethod.POST, "/web/users").hasAuthority("admin")

                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .build();
    }

    //    @Bean
    public UserDetailsService inMemoryUserDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories
                .createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsManager jdbcUserDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public ApplicationRunner adminInitializer(UserDetailsManager userManager,
                                              PasswordEncoder encoder) {
        return args -> {
            if (!userManager.userExists("admin")) {
                log.info("Admin user is created");
                userManager.createUser(User.builder()
                        .username("admin")
                        .password(encoder.encode("admin"))
                        .authorities("admin")
                        .build());
            }
        };
    }
}
