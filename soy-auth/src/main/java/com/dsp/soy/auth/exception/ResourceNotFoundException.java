package com.dsp.soy.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class ResourceNotFoundException extends AuthenticationException {

    public ResourceNotFoundException(String msg) {
        super(msg);
    }

    public ResourceNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
