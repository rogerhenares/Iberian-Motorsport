package com.iberianmotorsports.service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new SteamTokenValidation(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests()
                    .requestMatchers("/public/**", "/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(new SteamLoginConfiguration());
                http.cors().and().csrf().disable();
        return http.build();
    }

}



