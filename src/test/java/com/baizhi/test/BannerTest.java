package com.baizhi.test;

import com.baizhi.Application;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Id;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class BannerTest {
    @Autowired
    private BannerService bannerService;

    @Test//查找所有轮播图
    public void test1(){
        List<Banner> allBanner = bannerService.findAllBanner();
        for (Banner banner : allBanner) {
            System.out.println("*******"+banner);
        }
    }
    @Test
    public void test2(){
        Integer page = bannerService.totalPage();
        System.out.println("共"+page+"页");
    }
    @Test//分页查询
    public void test3(){
        List<Banner> page = bannerService.findByPage(0, 3, "id", "desc");
    }


    }


