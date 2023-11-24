package org.jugenfeier.contacts.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
@EnableWebSecurity
@ConditionalOnProperty(name = "org.jugenfeier.contact.security.enabled", havingValue = "true", matchIfMissing = true)
public class WebSecurityConfig {

    private final static String[] SWAGGER_METHODS_PATTERN = {
            "/swagger-ui/**",
            "/v3/**"
    };

    private final static String PUBLIC_METHODS_PATTERN = "/public/**";

    private final static String[] SECURE_METHODS_PATTERN = {
            "/contact",
            "/contact/**"
    };

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy(){
        return new NullAuthenticatedSessionStrategy();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(Customizer.withDefaults())
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers(SWAGGER_METHODS_PATTERN).permitAll()
                        .requestMatchers(PUBLIC_METHODS_PATTERN).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, SECURE_METHODS_PATTERN).permitAll()
                        .requestMatchers(HttpMethod.GET, SECURE_METHODS_PATTERN).permitAll()
                        .requestMatchers(HttpMethod.POST, SECURE_METHODS_PATTERN).permitAll()
                        .requestMatchers(HttpMethod.PUT, SECURE_METHODS_PATTERN).permitAll()
                        .requestMatchers(HttpMethod.DELETE, SECURE_METHODS_PATTERN).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .build();
    }
}
