package com.dsp.soy.code.gen.controller;

import com.dsp.soy.code.gen.entity.Member;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/code/gen")
public class CodeGenController {

    @GetMapping(value = "/test")
    public String test(Member member) {
        return member.toString();
    }
}
