package com.baizhi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.baizhi.dao")
public class Application {
    public static void main(String[] args) {
        //启动Springboot的内部容器
        SpringApplication.run(Application.class);
    }
}
