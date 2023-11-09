package com.teamchallenge.marketplace.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final static String[] WHITE_LIST={
            "/api/v1/auth/**",
            "/api/v1/"
    };

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring security filter chain");
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    logger.info("Authorizing requests");
                    auth.requestMatchers(WHITE_LIST)
                            .permitAll();
                    auth.anyRequest()
                            .authenticated();
                })
                .formLogin(withDefaults())
                .oauth2Login(withDefaults());
        // .sessionManagement(session -> session
        //         .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        //.authenticationProvider(authenticationProvider)
        //.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
