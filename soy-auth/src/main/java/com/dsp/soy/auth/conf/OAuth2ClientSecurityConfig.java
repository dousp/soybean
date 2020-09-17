package com.dsp.soy.auth.conf;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class OAuth2ClientSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.oauth2Client(oauth2 -> oauth2
        //     .clientRegistrationRepository(this.clientRegistrationRepository())
        //     .authorizedClientRepository(this.authorizedClientRepository())
        //     .authorizedClientService(this.authorizedClientService())
        //     .authorizationCodeGrant(codeGrant -> codeGrant
        //             .authorizationRequestRepository(this.authorizationRequestRepository())
        //             .authorizationRequestResolver(this.authorizationRequestResolver())
        //             .accessTokenResponseClient(this.accessTokenResponseClient())
        //     )
        // );
    }
    //
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
    //     return authorizedClientManager;
    // }
}