package com.dsp.soy.uaa.rest;

import com.dsp.soy.uaa.conf.SecurityProperties;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 接收请求以及渲染模板
 */
@Controller
@RequiredArgsConstructor
public class OauthController {

    private final @NonNull SecurityProperties securityProperties;

    @RequestMapping("${uaa.security.oauth.login-page:/oauth/login}")
    public String loginView(Model model, HttpServletRequest request) {
        model.addAttribute("action", securityProperties.getLoginProcessingUrl());
        return "form-login";
    }

}
