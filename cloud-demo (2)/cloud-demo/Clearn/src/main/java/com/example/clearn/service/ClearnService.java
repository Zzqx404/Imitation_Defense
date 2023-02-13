package com.example.clearn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


/**
 * @author xinxi
 * @create 2022-11-27 19:41
 */
@Service
public class ClearnService {


    @Autowired
    KillServer killServer;
    // 先测试cmd 再去测试异步的问题
    public String testCMD(String prefix) {

        Set<Integer> ports = new HashSet<>();

        ports.add(8091);

        killServer.ports = ports;

        for (Integer pid:
             killServer.ports) {
            System.out.println("----->"+pid);
            killServer.start(pid);
        }
        return "OK";
    }


}



