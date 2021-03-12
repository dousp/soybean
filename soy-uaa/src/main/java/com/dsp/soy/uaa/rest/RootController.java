package com.dsp.soy.uaa.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dsp
 * @date 2020-10-05
 */
@RestController
@RequestMapping("/")
public class RootController {

    @GetMapping(value = "")
    public Object root(){
        //获取用户身份信息
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    @GetMapping(value = "/demo/r1")
    @PreAuthorize("hasAnyAuthority('p1','p2')")
    public Object r1(){
        //获取用户身份信息
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
