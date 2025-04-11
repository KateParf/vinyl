package com.example.vinyl.config;

import com.example.vinyl.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFIlter;

    private final UserService userService;

    private final CustomLogoutHandler customLogoutHandler;


    public SecurityConfig(JwtFilter jwtFIlter,
                          UserService userService, CustomLogoutHandler customLogoutHandler) {

        this.jwtFIlter = jwtFIlter;
        this.userService = userService;
        this.customLogoutHandler = customLogoutHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login","/register", "/refresh_token")
                            .permitAll();
                    auth.requestMatchers("/genres").hasRole("ADMIN");
                    auth.anyRequest().authenticated();
                })
                .userDetailsService(userService)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .addFilterBefore(jwtFIlter, UsernamePasswordAuthenticationFilter.class)
                .logout(log -> {
                    log.logoutUrl("/logout");
                    log.addLogoutHandler(customLogoutHandler);
                    log.logoutSuccessHandler((request, response, authentication) ->
                            SecurityContextHolder.clearContext());
                });

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();
    }

}