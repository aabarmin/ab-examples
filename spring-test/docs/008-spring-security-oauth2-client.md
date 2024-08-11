# Spring Security OAuth2 Client aka Google Login

In order to allow logging in via Google Login it is necessary to add a dependency: 

```groovy
implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
```

When the dependency is added, the next step is to enable OAuth2 login in the Spring Security configuration: 

```java
    public SecurityFilterChain webFilterChain(HttpSecurity http) {
        return http
                .oauth2Login(Customizer.withDefaults()) // add just this
                .build();
    }
```

And the final step is to add the registration to the `application.yml`:

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: ${OAUTH_GOOGLE_CLIENT_ID}
            client-secret: ${OAUTH_GOOGLE_CLIENT_SECRET}
```

That's it! 