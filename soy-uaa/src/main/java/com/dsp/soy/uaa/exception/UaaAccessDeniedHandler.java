package com.dsp.soy.uaa.exception;

import com.alibaba.fastjson.JSON;
import com.dsp.soy.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 配合uaaWebResponseExceptionTranslator
 */
@Component
@Slf4j
public class UaaAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("UaaAccessDeniedHandler: "+ accessDeniedException.getMessage());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
        Result<String> result = Result.failed(accessDeniedException.getMessage());
        result.setPath(request.getServletPath());
        response.getWriter().write(JSON.toJSONString(result));
    }
}
