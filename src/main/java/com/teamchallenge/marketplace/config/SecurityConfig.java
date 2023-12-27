package com.teamchallenge.marketplace.config;

import com.teamchallenge.marketplace.handler.CustomOAuth2SuccessHandler;
import com.teamchallenge.marketplace.security.oauth2.OAuth2UserDetails;
import com.teamchallenge.marketplace.services.auth.OAuth2UserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final OAuth2UserDetailsService oAuth2UserDetailsService;
    private final SimpleUrlAuthenticationSuccessHandler successHandler;
    private final SimpleUrlAuthenticationFailureHandler failureHandler;
    private final JwtService jwtService;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
    private final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final static String[] WHITE_LIST={
            "/api/v1/auth/**",
            "/login",
    };
    @Autowired


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

                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/qwerty", true)
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(oAuth2UserDetailsService))
                        .successHandler((request, response, authentication) -> {
                            OAuth2UserDetails oAuth2UserDetails = (OAuth2UserDetails) authentication.getPrincipal();
                            String jwtToken = jwtService.generateToken(oAuth2UserDetails);
                            response.setHeader("Authorization", "Bearer " + jwtToken);
                            System.out.println("User registered:" + jwtToken);
                            customOAuth2SuccessHandler.onAuthenticationSuccess(request, response, authentication);
                        })
                        .failureHandler(failureHandler))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }



}
