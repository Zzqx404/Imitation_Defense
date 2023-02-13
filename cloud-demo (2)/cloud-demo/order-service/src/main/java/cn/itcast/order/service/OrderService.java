package cn.itcast.order.service;

import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import cn.itcast.order.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class OrderService {
    /**
     * OrderService Service层
     */
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RestTemplate restTemplate;

    public Order queryOrderById(Long orderId) {
        /*
            查询本地数据后，再调用OS获取其他微服务数据
         */

        // 1.本地查询订单
        Order order = orderMapper.findById(orderId);
        log.info("123");
        //2.调用OS,本地调用使用IP+port 可扩展.....
        String url = "http://localhost:8050/os/toUs/"+order.getUserId();

        //2.2.发送http请求，实现远程调用
        User user = restTemplate.getForObject(url, User.class);

        //3.封装user到Order
        order.setUser(user);
        // 4.返回
        return order;
    }
}
