package com.example.controller.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class MyService{
    
    Map<String,String> Java_map_down = new HashMap();
    Map<String,String> Python_map_down = new HashMap();
    Map<String,String> nodeJS_map_down = new HashMap();
    Map<String,String> Python2_map_down = new HashMap();


    Map<String,String> Java_map_up = new HashMap();
    Map<String,String> Python_map_up = new HashMap();
    Map<String,String> nodeJS_map_up = new HashMap();
    Map<String,String> Python2_map_up = new HashMap();
    // 服务状态
    Map<String,Integer> User_service_map = new HashMap<>();

    // 服务状态
    Map<String,Integer> Order_service_map = new HashMap<>();

    // 是否为拟态化的map 要进行多线程同步控制
    @Autowired
    Test test;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KillServer killServer;


    private ThreadDemo threadDemo;

    private ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

//    public Test test_1 = new Test();

    public MyService(){


        // 所有的IP与其对应的服务标识（服务标识,Eureka设置上下线）
        Java_map_down.put("User", "8081");
        Java_map_up.put("User","C:\\Users\\万佳欣\\Desktop\\zuul版本\\cloud-demo\\user-service\\target\\app.jar");
        // Python表
        Python_map_down.put("User","8091");
        Python_map_up.put("User","C:\\Users\\万佳欣\\Desktop\\zuul版本\\py\\pyuser\\py_user.py");
        // nodeJS表
        nodeJS_map_down.put("User","8011");
        nodeJS_map_up.put("User","C:\\Users\\万佳欣\\Desktop\\zuul版本\\node\\nodeuser\\user.js");
        // Python2表


        // -1有问题，0备选，1正在运行中
        User_service_map.put("Java_User",1);
        User_service_map.put("Python_User",1);
        User_service_map.put("nodeJS_User",1);
        User_service_map.put("Python2_User",0);
        User_service_map.put("Python3_User",0);

        // -1有问题，0备选，1正在运行中
        Order_service_map.put("Java_Order",1);
        Order_service_map.put("Python_Order",0);
        Order_service_map.put("nodeJS_Order",0);
        Order_service_map.put("Python2_Order",0);
        Order_service_map.put("Python3_Order",0);

        // 将线程对象创建
        map.put("User", "NO");
        map.put("Order", "No");
        // map.put("nodeJS", "No");
        test.setMap(map);
    }

    // 开启随机
    public void randServer(){


        // 开启线程,值传递
        threadDemo = new ThreadDemo(test);
        threadDemo.start();

    }


    //方法名字之前有引用就不改了，参数列表实现了扩展
    public String toDownUser(String Language,String Microservice){

        String[] res = Language.split("_");
        // 受到攻击了，统计个数
        String status = test.getMap().get(Microservice);

        if (status.equals("YES"))// 为拟态
        {
            test.getCm().incrementAndGet();
        }else{// 非拟态
            test.getCu().incrementAndGet();
        }

        String port = null;
        log.info("语言类型："+res[0]);
        // 对应语言问题,怎么找到对应的表,有扩展性,上线问题,判断那个需要上线,那个已经下线,还得再建立一个对应的map去找,除非是使用redis好解决 但是更麻烦了
        if(res[0].equals("Java")){
            port = Java_map_down.get(Microservice);
            User_service_map.put("Java",-1);
        }else if(res[0].equals("Python")){
            port = Python_map_down.get(Microservice);
            User_service_map.put("Python",-1);
        }else if(res[0].equals("nodeJS")){
            port = nodeJS_map_down.get(Microservice);
            User_service_map.put("nodeJS",-1);
        }else{
            // 第四个语言
            port = null;
        }

        log.info("定位完成.....");



        // 下线函数

        Set<Integer> ports = new HashSet<>();
        ports.add(Integer.parseInt(port));
        log.info("端口集合.....");
        killServer.ports = ports;
        for (Integer pid:ports
             ) {
            log.info("pid:" + pid);
            killServer.start(pid);
        }
        log.info("错误裁决,完成下线，解决延迟");

        return "OK";
    }

    // 上线函数
    public String up_Service(String Language,String Microservice){
        log.info("下线完成，服务上线");
        try {
            //错的  根据语言选择执行
            Process process = null;

            switch(Language) {
                case "Java":
                    process = Runtime.getRuntime().exec("java -jar " + Java_map_up.get(Microservice));
                    break;
                case "Python":
                    process = Runtime.getRuntime().exec("python " + Python_map_up.get(Microservice));
                    break;
                case "nodeJS":
                    process = Runtime.getRuntime().exec("node " + nodeJS_map_up.get(Microservice));
                    break;
            }
            InputStream inputStream = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream,"GBK");
            BufferedReader bufferedReader = new BufferedReader(isr);

            String line = null;
            while((line = bufferedReader.readLine())!=null){
                log.info(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "OK";
    }

    public void randDown(String Microservice){
        Map<String,Integer> map;

        //遍历微服务的状态列表,现在只是为了方便演示
        if(Microservice.equals("User"))
            map = User_service_map;
        else
            map = Order_service_map;

        Set<String> set = map.keySet();
        List<String> list = new ArrayList<>();
        // 将正常的运行的状态存入list中
        for (String item:set
             ) {

            if(map.get(item)==1){
                list.add(item);
            }
        }

        Map downMap = null;
        // 随机选取-- 反向操作随机选取一个，除选取的之外下线
        Random rand = new Random();
        int index = rand.nextInt(list.size());
        for (int i = 0; i <list.size(); i++) {
            if(i!=index){
                // 下线
                String[] res = list.get(index).split("_"); // 先判断语言， 微服务已经确定了 Java
                switch (res[0]){

                    case "Java":
                        downMap = Java_map_down;
                        break;
                    case "Python":
                        downMap = Python_map_down;
                        break;
                    case "nodeJS":
                        downMap = nodeJS_map_down;
                        break;

                }
            }
        }
        // 确定了downmap
        log.info("随机过程定位完成.....");

        // 下线函数
        String port = (String) downMap.get(Microservice);
        Set<Integer> ports = new HashSet<>();
        ports.add(Integer.parseInt(port));
        log.info("端口集合.....");
        killServer.ports = ports;
        for (Integer pid:ports
        ) {
            log.info("pid:" + pid);
            killServer.start(pid);
        }
    }

    public void randUp(String Microservice){

        Map<String,Integer> map;

        //遍历微服务的状态列表,现在只是为了方便演示
        if(Microservice.equals("User"))
            map = User_service_map;
        else
            map = Order_service_map;


        Set<String> set = map.keySet();
        List<String> list = new ArrayList<>();
        // 将正在待机的状态存入list中
        for (String item:set
        ) {

            if(map.get(item)==0){
                list.add(item);
            }
        }
        // 随机从待机状态中选取选取两个上线     ----强化学习
        Random rand = new Random();
        int a = rand.nextInt(list.size());
        int b;
        while (true){
            b = rand.nextInt(list.size());
            if(b != a){
                break;
            }
        }
        // a 是选取的第一个下标利用下标进行判断是哪个语言
        String[] res = list.get(a).split("_");
        String Language = res[0];

        Map upMap = null;
//        switch (res[0]){
//
//            case "Java":
//                upMap = Java_map_up;
//                break;
//            case "Python":
//                upMap = Python_map_up;
//                break;
//            case "nodeJS":
//                upMap = nodeJS_map_up;
//                break;
//
//        }
        // 调用函数进行上线
        up_Service(Language,Microservice);
        //状态map修改未完成
    }


    }
