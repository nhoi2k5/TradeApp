package com.App.TradeApp.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping()
    public String Home(){
        return "Dit con me may con cho cong san";
    }
}
