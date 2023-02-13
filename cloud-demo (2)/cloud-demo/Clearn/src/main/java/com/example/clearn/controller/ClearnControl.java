package com.example.clearn.controller;

import com.example.clearn.service.ClearnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author xinxi
 * @create 2022-11-27 19:22
 */

@RestController
public class ClearnControl {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ClearnService clearnService;


    // 测试controller访问功能
    @GetMapping("/hello")
    public String hello(){
        return clearnService.testCMD("user");
    }
}
