package cn.itcast.demo.contreoller;

import cn.itcast.demo.pojo.User;
import cn.itcast.demo.service.OS_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/os")
public class OS_Controller {



    @Autowired
    private OS_Service os_service;

    @GetMapping("/toUs/{userId}")
    public User toUs(@PathVariable("userId")Long userId){

        return os_service.Adjudication(userId);

    }

}
