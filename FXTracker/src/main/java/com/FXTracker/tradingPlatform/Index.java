package com.FXTracker.tradingPlatform;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Index {

    @GetMapping("/")
    public String getPage(){

        return "Welcome to assets trading app.";
    }
}
