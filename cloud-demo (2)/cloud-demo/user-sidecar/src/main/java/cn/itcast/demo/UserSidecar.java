package cn.itcast.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;

@SpringBootApplication(exclude= DataSourceAutoConfiguration.class)
@EnableSidecar

public class UserSidecar {
    public static void main(String[] args) {
        SpringApplication.run(UserSidecar.class, args);
    }
}
