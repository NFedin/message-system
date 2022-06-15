package com.nikfedin.messagesystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StartPageController {
    @GetMapping("/")
    public String startPage(){
        return "Hello ABRA!";
    }
}
