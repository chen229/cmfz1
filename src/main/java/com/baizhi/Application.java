package com.baizhi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.baizhi.dao")
public class Application {
    public static void main(String[] args) {
        //启动Springboot的内部容器
        SpringApplication.run(Application.class);
    }

    @Bean//把Jedis对象交由Bean工厂创建
    public Jedis getJedis(){
        return new Jedis("192.168.136.141",6379);//redis的ip和端口号
    }
}
