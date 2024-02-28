package com.example.demojwt.demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * DemoController
 */
@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class DemoController {

    @PostMapping("demo")
    public String welcom() {
        return "Welcome to JWT Spring Boot Starter!";
    }

}