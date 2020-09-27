package com.dsp.soy.auth.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.security.interfaces.RSAPublicKey;

@EnableWebSecurity
public class OAuth2ClientSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
           .antMatchers("/webjars/**");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests(authorize -> authorize
                .antMatchers("/message/**").hasAuthority("SCOPE_message:read")
            )
            .oauth2ResourceServer((oauth2ResourceServer) ->
                    oauth2ResourceServer.jwt(jwt ->
                            jwt.decoder(jwtDecoder())
                    )
            )
        ;
    }

    @Value("${spring.security.oauth2.resourceserver.jwt.key-value}")
    RSAPublicKey key;

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.key).build();
    }


    // @Bean
    // public OAuth2AuthorizedClientManager authorizedClientManager(
    //         ClientRegistrationRepository clientRegistrationRepository,
    //         OAuth2AuthorizedClientRepository authorizedClientRepository) {
    //
    //     OAuth2AuthorizedClientProvider authorizedClientProvider =
    //             OAuth2AuthorizedClientProviderBuilder.builder()
    //                     .authorizationCode()
    //                     .refreshToken()
    //                     .clientCredentials()
    //                     .password()
    //                     .build();
    //
    //     DefaultOAuth2AuthorizedClientManager authorizedClientManager =
    //             new DefaultOAuth2AuthorizedClientManager(
    //                     clientRegistrationRepository, authorizedClientRepository);
    //     authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
    //
    //     // Assuming the `username` and `password` are supplied as `HttpServletRequest` parameters,
    //     // map the `HttpServletRequest` parameters to `OAuth2AuthorizationContext.getAttributes()`
    //     authorizedClientManager.setContextAttributesMapper(contextAttributesMapper());
    //     return authorizedClientManager;
    // }
    //
    // private Function<OAuth2AuthorizeRequest, Map<String, Object>> contextAttributesMapper() {
    //     return authorizeRequest -> {
    //         Map<String, Object> contextAttributes = Collections.emptyMap();
    //         HttpServletRequest servletRequest = authorizeRequest.getAttribute(HttpServletRequest.class.getName());
    //         String username = servletRequest.getParameter(OAuth2ParameterNames.USERNAME);
    //         String password = servletRequest.getParameter(OAuth2ParameterNames.PASSWORD);
    //         if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
    //             contextAttributes = new HashMap<>();
    //
    //             // `PasswordOAuth2AuthorizedClientProvider` requires both attributes
    //             contextAttributes.put(OAuth2AuthorizationContext.USERNAME_ATTRIBUTE_NAME, username);
    //             contextAttributes.put(OAuth2AuthorizationContext.PASSWORD_ATTRIBUTE_NAME, password);
    //         }
    //         return contextAttributes;
    //     };
    // }
    //
    // @Bean
    // public ClientRegistrationRepository clientRegistrationRepository() {
    //     return new InMemoryClientRegistrationRepository(this.googleClientRegistration());
    // }
    //
    // private ClientRegistration googleClientRegistration() {
    //     return ClientRegistration.withRegistrationId("google")
    //             .clientId("google-client-id")
    //             .clientSecret("google-client-secret")
    //             .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
    //             .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
    //             .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
    //             .scope("openid", "profile", "email", "address", "phone")
    //             .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
    //             .tokenUri("https://www.googleapis.com/oauth2/v4/token")
    //             .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
    //             .userNameAttributeName(IdTokenClaimNames.SUB)
    //             .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
    //             .clientName("Google")
    //             .build();
    // }
    //
    // @Bean
    // public UserDetailsService users() {
    //     UserDetails user = User.withDefaultPasswordEncoder()
    //             .username("user1")
    //             .password("password")
    //             .roles("USER")
    //             .build();
    //     return  new InMemoryUserDetailsManager(user);
    // }
}