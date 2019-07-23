package com.baizhi.test;

import com.baizhi.Application;
import com.baizhi.entity.Maps;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static java.time.OffsetDateTime.now;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserTest {
    @Autowired
    private UserService userService;

    @Test
    public void test1(){
        Map<String, Object> allUser = userService.findAllUser(0, 3);
        System.out.println(
                "********"+allUser
        );
    }
    @Test
    public void test2(){
        List<User> all = userService.findAll();
        for (User user : all) {
            System.out.println(user+"&&&&&");
        }

    }
    @Test
    public void test4(){
        List<Maps> nv = userService.findBySexAndPro("女");
        for (Maps maps : nv) {
            System.out.println(maps);
        }
    }
    @Test
    public void test5(){
        Integer count = userService.findBySex6("女");
        System.out.println(count);
    }
    @Test
    public void test6(){
        System.out.println(now()+"*******");
    }
}
