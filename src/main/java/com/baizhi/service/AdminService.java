package com.baizhi.service;

import com.baizhi.entity.Admin;

import javax.servlet.http.HttpSession;

public interface AdminService {
    //登录
    String findByUsernameAndPwd(String username,
                                String password,
                                String enCode,
                                HttpSession session);
    //用户注册
    void insertAdmin(Admin admin);
    //根据用户名查找
    Admin findByUsername(String username);
    //发送验证码
    void sendMessage(HttpSession session);
}
