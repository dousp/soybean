package com.dsp.soy.uaa.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dsp
 * @date 2020-10-05
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping(value = "/r1")
    @PreAuthorize("hasAuthority('p2')")//拥有p1权限方可访问此url
    public Object r1(){
        //获取用户身份信息
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
