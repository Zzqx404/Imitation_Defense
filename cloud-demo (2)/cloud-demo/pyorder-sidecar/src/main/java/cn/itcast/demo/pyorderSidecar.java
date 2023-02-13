package cn.itcast.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude= DataSourceAutoConfiguration.class)
@EnableSidecar
public class pyorderSidecar {
    public static void main(String[] args) {
        SpringApplication.run(pyorderSidecar.class, args);

    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


}