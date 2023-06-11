package com.iberianmotorsports.service.configuration;

import com.iberianmotorsports.service.service.AuthService;
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

    private final static String BASIC_USER = "BASIC_USER";
    private final static String ADMIN = "ADMIN";

    private final AuthService authService;

    public SecurityConfig(AuthService authService) {
        this.authService = authService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //.cors().and()
                .addFilterBefore(new SteamTokenValidation(authService), BasicAuthenticationFilter.class)
                .authorizeHttpRequests()
                    .requestMatchers("/public/**").permitAll()
                    //.requestMatchers("/admin/**").hasAnyRole(ADMIN)
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(new SteamLoginConfiguration());
        //http.cors().and().csrf().disable();
        return http.build();
    }
}



