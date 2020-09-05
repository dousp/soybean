package com.dsp.code.gen.controller;

import com.dsp.code.gen.entity.Member;
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
