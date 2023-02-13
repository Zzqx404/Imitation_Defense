package com.example.controller.service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


public class ThreadDemo extends Thread{

    @Autowired
    public MyService myService;

    private Test test1;

    public ThreadDemo(Test test){

        test1 = test;

    }


    // 微服务状态Map,(name,0/1) 是否为拟态化
    //private Map<String,String> map;
    
    // 结果集合
    List<String> res;
    //
//    public void setMap(Map map){
//        this.map = map;
//    }

    // 1秒
    final long timeInterval = 10000;
    public void run() {
        // 线程内部休眠实现周期执行

        System.out.println(Thread.currentThread().getName()+"--------"+test1);


        while (true){
            try {

                // 周期休眠,先进行休眠，等待周期的变化，在进行随机选择
                Thread.sleep(timeInterval);

                // 进行随机选择
                res = randomChoose();

                // 直接根据结果进行上线和下线微服务
                String Microservice;
                // 拟态就是线下其他的服务

                // 非拟态就是上线其他的服务
                for (int i = 0; i < res.size(); i++) {
                    Microservice = res.get(i);
                    test1.changeMap(Microservice,"YES");
                    myService.randUp(Microservice);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 创建选择函数
    public List randomChoose() {
        // 拟态集合
        List<String> mimesis = new ArrayList<>();
        // 非拟态集合
        List<String> non_mimesis = new ArrayList<>();

        // 遍历Map函数
        Set<String> s1 = test1.getMap().keySet();
        for (String i : s1
        ) {
            if (test1.getMap().get(i) == "YES") {// 拟态
                mimesis.add(i);
            } else {
                non_mimesis.add(i);
            }
        }

        // 随机选取
        Random rand = new Random();
        //  得添加点异常的控制----------------------------------而且没有进行公式的计算
        System.out.println("Mlength："+mimesis.size());
        System.out.println("NMlength："+mimesis.size());

        // 计算选取个线性函数 y = ax + z a为常数
        double y_temp = 0.8*test1.getCu().get() + test1.getCm().get();
        //向上取整
        int y  = (int) Math.ceil(y_temp);
        // 非拟态节点个体总数
        int high = non_mimesis.size();
        int nd  = Math.min(Math.max(y,1),high);
        // 随机选取非拟态的 准备进入拟态
        List<String> res = new ArrayList<>();
        // 到底是选取哪几个不就是对接这个强化学习了







        return res;

    }
}
