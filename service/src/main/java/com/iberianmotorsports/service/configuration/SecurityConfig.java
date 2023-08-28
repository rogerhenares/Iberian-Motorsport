package com.iberianmotorsports.service.configuration;

import com.iberianmotorsports.service.service.AuthService;
import com.iberianmotorsports.service.utils.RoleType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;


@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {
        "com.iberianmotorsports.service.configuration",
        "com.iberianmotorsports.service.service"})
public class SecurityConfig  {

    private static final String[] AUTH_WHITE_LIST = {
            "/public/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/swagger-resources/**"
    };

    private static final String[] STEWARDS_URL_LIST = {
            "/sanction",
            "/sanction/delete/**"
    };

    private final AuthService authService;

    public SecurityConfig(AuthService authService) {
        this.authService = authService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        RequestMatcher adminUrls = new AntPathRequestMatcher("/**/admin/**");
        RequestMatcher publicUrls = new AntPathRequestMatcher("/**/public/**");

        http
                .addFilterBefore(new SteamTokenValidation(authService), BasicAuthenticationFilter.class)
                .authorizeHttpRequests()
                    .requestMatchers(AUTH_WHITE_LIST).permitAll()
                    .requestMatchers(publicUrls).permitAll()
                    .requestMatchers(STEWARDS_URL_LIST).hasAnyAuthority(RoleType.ADMIN.getValue(),
                                                                        RoleType.STEWARD.getValue())
                    .requestMatchers(adminUrls).hasAnyAuthority(RoleType.ADMIN.getValue())
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint(new SteamLoginConfiguration())
                .and()
                .csrf().disable();
        return http.build();
    }
}



