package cn.itcast.demo.service;

import cn.itcast.demo.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class JOS_Service {

    @Autowired
    private RestTemplate restTemplate;

    public User Adjudication(Long userId){

        HashMap<User,Integer> map = new HashMap<>();
        // 集合实现
        List<User> users = new ArrayList<>();

        // 语言A
        User user1 =  restTemplate.getForObject("http://localhost:9100/user/getuser/"+userId,User.class);
        log.info("user1:{}",user1.toString());
        //Thread.currentThread().sleep(2000);

        // 语言B
        User user2 = restTemplate.getForObject("http://localhost:9100/user/getuser/"+userId,User.class);
        log.info("user2:{}",user2.toString());
        // 语言C......
        User user3 = restTemplate.getForObject("http://localhost:9100/user/getuser/"+userId,User.class);
        log.info("user3:{}",user3.toString());
        users.add(user1);
        users.add(user2);
        users.add(user3);
        log.info("users:{}",users.size());
        // 裁决已经结束了
        User user = getResult(users,map);
        // 找到问题点
        User user_err = null;
        for (User user_temp:users) {
            if(!user.equals(user_temp)){
                user_err = user_temp;
                break;
            }
        }
        log.info("错误裁决{}",user_err.toString());
        // 再次标记这个对象
        user_err.setLanguage(user_err.getLanguage()+"User");
        // 需要将这个错误结果反馈到control模块 通过restTemplate方式
        // String ans = restTemplate.getForObject("http://localhost:9200/down/"+user_err.getLanguage(),String.class);
        // 日志打印....

        return user;
    }

    // 找到多数结果，可扩展
    private User getResult(List<User> users,HashMap<User,Integer> map){

        //map.put(users.get(0),1);
        for (int i = 0; i < users.size();i++ ) {
            User user_list =  users.get(i);
            boolean flag = false;
            // 多数投票,重写了hashcode() 可以直接使用
            if(map.containsKey(user_list)){
                map.put(user_list, map.get(user_list)+1);
            }else {
                map.put(user_list,1);
            }
        }
        //投票结束，进行裁决
        User max_user = null;
        int max = 0;
        //  这里还是有点小问题的
        for (User user_temp:map.keySet())
        {
            // 遍历访问User然后打印
            log.info("个数：{}",map.keySet().size());
            if(map.get(user_temp) > max){
                max = map.get(user_temp);
                max_user = user_temp;
            }
        }
        // 标识多数解
        return max_user;
    }

}
