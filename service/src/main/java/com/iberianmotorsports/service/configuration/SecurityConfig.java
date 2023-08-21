package com.iberianmotorsports.service.configuration;

import com.iberianmotorsports.service.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {
        "com.iberianmotorsports.service.configuration",
        "com.iberianmotorsports.service.service"})
public class SecurityConfig  {

    private final static String BASIC_USER = "BASIC_USER";
    private final static String ADMIN = "ADMIN";

    private static final String[] AUTH_WHITE_LIST = {
            "/public/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/swagger-resources/**"
    };

    private final AuthService authService;

    public SecurityConfig(AuthService authService) {
        this.authService = authService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //RequestMatcher adminUrls = new AntPathRequestMatcher("/**/admin/**");

        http
                .addFilterBefore(new SteamTokenValidation(authService), BasicAuthenticationFilter.class)
                .authorizeHttpRequests()
                    .requestMatchers(AUTH_WHITE_LIST).permitAll()
                    //.requestMatchers(adminUrls).hasAnyAuthority(ADMIN)
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new SteamLoginConfiguration());
        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .exposedHeaders("Authorization")
                        .allowCredentials(true);
            }
        };
    }
}



