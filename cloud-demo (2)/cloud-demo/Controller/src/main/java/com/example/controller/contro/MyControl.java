package com.example.controller.contro;

import com.example.controller.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MyControl{

    @Autowired
    private MyService myService;

    @Autowired
    private RestTemplate restTemplate;

    // 去访问Eureka的界面
    @GetMapping("/down_User/{Language}/{Microservice}")
    public String downService_User(@PathVariable("Language")String Language,@PathVariable("Microservice")String Microservice){

        String res = myService.toDownUser(Language,Microservice);

        return res;
    }

    @GetMapping("/up_User/{Language}/{Microservice}")
    public String upService_User(@PathVariable("Language")String Language,@PathVariable("Microservice")String Microservice){

        String res = myService.up_Service(Language,Microservice);

        return res;
    }

}
