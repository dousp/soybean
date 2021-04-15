package com.dsp.soy.auth.rest;

import com.dsp.soy.auth.conf.SecurityProperties;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 自定义授权的控制器
 * 写一个相同的端点,对 /oauth/confirm_access 进行覆盖
 */
@Controller
@SessionAttributes("authorizationRequest")  // 重要！
@RequiredArgsConstructor
public class AuthorizationController {

    private final @NonNull SecurityProperties securityProperties;

    @RequestMapping("/oauth/confirm_access")
    public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
        ModelAndView view = new ModelAndView();
        if (authorizationRequest==null){
            view.setViewName("redirect:"+securityProperties.getLoginPage());
            return view;
        }
        view.setViewName("authorization");
        view.addObject("clientId", authorizationRequest.getClientId());
        // 传递 scope 过去,Set 集合
        view.addObject("scopes", authorizationRequest.getScope());
        // 拼接一下名字
        view.addObject("scopeName", String.join(",", authorizationRequest.getScope()));
        return view;
    }
}
