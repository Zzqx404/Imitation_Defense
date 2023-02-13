package com.example.clearn.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class testRandom {


    public String testStart() {

        ThreadDemo threadDemo1 = new ThreadDemo();

        threadDemo1.run();

        return "OK";
    }

}

class ThreadDemo extends Thread{

    // 微服务状态Map,(name,0/1) 是否为拟态化
    private Map<String,String> map;

    // 结果集合
    List<String> res;
    //
    public void setMap(Map map){
        this.map = map;
    }

    // 1秒
    final long timeInterval = 1000;

    public void run() {
        // 线程内部休眠实现周期执行
        while (true){
            try {

                // 周期休眠
                Thread.sleep(timeInterval);
                // 进行随机选择
                res = randomChoose();


                // 直接根据结果进行上线和下线微服务
                String Language;
                // 拟态就是线下其他的服务
                Language = res.get(0);
                // 根据这个语言下线即可
                // 非拟态就是上线其他的服务
                Language = res.get(1);
                // ........

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
        Set<String> s1 = this.map.keySet();
        for (String i : s1
        ) {
            if (map.get(i) == "YES") {// 拟态
                mimesis.add(i);
            } else {
                non_mimesis.add(i);
            }
        }

        // 随机选取
        Random rand = new Random();
        // 随机选取拟态的 准备进入非拟态
        String randomMim = mimesis.get(rand.nextInt(mimesis.size()));
        // 随机选取非拟态的 准备进入拟态
        String randomMim_non = non_mimesis.get(rand.nextInt(non_mimesis.size()));

        List res = new ArrayList();

        res.add(randomMim);
        res.add(randomMim_non);

        return res;
    }
}
