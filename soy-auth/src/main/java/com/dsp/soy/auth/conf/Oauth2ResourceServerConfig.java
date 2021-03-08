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

/**
 * @author dsp
 * @date 2020-10-03
 */
@EnableWebSecurity
public class Oauth2ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.oauth2.resourceserver.jwt.key-value}")
    RSAPublicKey key;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/webjars/**");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests(authorize ->
                authorize.antMatchers("/actuator/**").permitAll()
                         .antMatchers("/message/**").hasAuthority("SCOPE_message:read")
                         .anyRequest().authenticated()
            )
            // .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
            .oauth2ResourceServer(oauth2 ->
                    oauth2 .jwt(jwt ->
                            jwt.decoder(jwtDecoder()))
            )
        ;
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.key).build();
    }


}
