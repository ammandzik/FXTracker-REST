package com.FXTracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
 class Index {

    @GetMapping("/")
    public String getPage(){

        return "Welcome to assets trading app.";
    }
}
