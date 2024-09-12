package com.bjit.book_service.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello-book")
public class Hello {
    @GetMapping("/get")
    public String hello(){
        return "Hello";
    }
}
