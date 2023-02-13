package cn.itcast.zuul.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class index {


    @GetMapping("/index")
    public String getIndex(){
        return "hello";
    }
}
