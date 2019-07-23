package com.baizhi.test;

import com.baizhi.Application;
import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AdminTest {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private AdminService adminService;

    @Test
    public void test1(){
        Admin admin=new Admin();
        admin.setUsername("admin");
        admin.setPassword("111111");
        Admin admin1 = adminDao.selectOne(admin);
        System.out.println(admin1);
    }
    @Test
    public void test2(){
        adminDao.insertSelective(new Admin(null,"ssss","111111","小红"));
    }
    @Test
    public void test3(){
        Admin chen = adminService.findByUsername("chen");
        System.out.println(chen+"____");
    }

}
