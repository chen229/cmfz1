package com.baizhi.test;

import com.baizhi.Application;
import io.goeasy.GoEasy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class GoEasyTest {

    @Test
    public void test(){
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-872e282a2a424daf8e504d04f16b0a24");
        goEasy.publish("my_channel", "Hello, World!");
    }
    @Test
    public void test1(){
        Map<String,Object> map=new HashMap<>();
    }
}
