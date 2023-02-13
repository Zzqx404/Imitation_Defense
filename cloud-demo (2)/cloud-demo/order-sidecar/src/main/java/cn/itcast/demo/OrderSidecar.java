package cn.itcast.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude= DataSourceAutoConfiguration.class)
@EnableSidecar
public class OrderSidecar {
    public static void main(String[] args) {
        SpringApplication.run(OrderSidecar.class, args);
    }
    /**
     * 创建RestTemplate并注入Spring容器
     */
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}