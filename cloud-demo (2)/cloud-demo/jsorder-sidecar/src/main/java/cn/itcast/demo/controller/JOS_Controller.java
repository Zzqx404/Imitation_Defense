package cn.itcast.demo.controller;

import cn.itcast.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/JOS")
public class JOS_Controller {


    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/toUs/{userId}")
    public User toUs(@PathVariable("userId")Long userId){
        //  可扩展至service层
           User user =  restTemplate.getForObject("http://localhost:9100/user/getuser/"+userId,User.class);
           return user;
    }

}
