package com.iberianmotorsports.service.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.client.registration.steam.client-id}")
    private String steamClientId;

    @Value("${spring.security.oauth2.client.registration.steam.client-secret}")
    private String steamClientSecret;

    @Value("${spring.security.oauth2.client.registration.steam.redirect-uri}")
    private String steamRedirectUri;

    @Value("${spring.security.oauth2.client.provider.steam.authorization-uri}")
    private String steamAuthUri;

    @Value("${spring.security.oauth2.client.provider.steam.token-uri}")
    private String steamTokenUri;

    @Value("${spring.security.oauth2.client.provider.steam.user-info-uri}")
    private String steamUserInfoUri;

    @Value("${spring.security.oauth2.client.provider.steam.jwk-set-uri}")
    private String steamJwkSetUri;

    @Value("${spring.security.oauth2.client.provider.steam.issuer-uri}")
    private String steamIssuerUri;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/login").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/openid/id")
                                .defaultSuccessUrl("/user")
                )
                .oauth2Client(oauth2Client ->
                        oauth2Client
                                .clientRegistrationRepository(clientRegistrationRepository())
                                .authorizedClientRepository(authorizedClientRepository())
                )
                .build();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration registration = ClientRegistration
                .withRegistrationId("steam")
                .clientId(steamClientId)
                .clientSecret(steamClientSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri(steamRedirectUri)
                .scope("openid", "profile", "steamid")
                .authorizationUri(steamAuthUri)
                .tokenUri(steamTokenUri)
                .userInfoUri(steamUserInfoUri)
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .jwkSetUri(steamJwkSetUri)
                .issuerUri(steamIssuerUri)
                .build();
        return new InMemoryClientRegistrationRepository(registration);
    }


    @Bean
    public OAuth2AuthorizedClientRepository authorizedClientRepository() {
        return new HttpSessionOAuth2AuthorizedClientRepository();
    }
}



