package com.iberianmotorsports.service.configuration;

import com.iberianmotorsports.service.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {
        "com.iberianmotorsports.service.configuration",
        "com.iberianmotorsports.service.service"})
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //.cors().and()
                .addFilterBefore(new SteamTokenValidation(userService), BasicAuthenticationFilter.class)
                .authorizeHttpRequests()
                    .requestMatchers("/public/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(new SteamLoginConfiguration());
        return http.build();
    }
}



