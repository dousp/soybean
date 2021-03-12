package com.dsp.soy.uaa.exception.oauth;

import com.alibaba.fastjson.JSON;
import com.dsp.soy.uaa.common.Result;
import com.dsp.soy.uaa.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
@Component
@Slf4j
public class UaaAuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws ServletException {
        log.info("UaaAuthExceptionEntryPoint: "+ authException.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
        Result<String> result = Result.failed(ResultCode.CLIENT_AUTHENTICATION_FAILED);
        result.setPath(request.getServletPath());
        result.setDetail(authException.getMessage());
        try {
            response.getWriter().print(JSON.toJSONString(result));
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServletException("Error writing results");
        }
    }
}
