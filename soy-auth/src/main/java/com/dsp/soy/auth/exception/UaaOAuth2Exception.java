package com.dsp.soy.auth.exception;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@JsonSerialize(using = UaaOauth2ExceptionSerializer.class)
public class UaaOAuth2Exception extends OAuth2Exception {

    public UaaOAuth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public UaaOAuth2Exception(String msg) {
        super(msg);
    }
}
