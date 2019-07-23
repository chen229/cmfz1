package com.baizhi.controller;

import com.alibaba.fastjson.JSON;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    //登录
    @RequestMapping(value = "/loginAdmin" ,produces="text/html;charset=utf-8")
    public String loginAdmin(String username, String password,
                             HttpSession session, String enCode,
                             HttpServletResponse response, HttpServletRequest request){
        String message = adminService.findByUsernameAndPwd(username, password, enCode, session);
        request.setAttribute("message",message);
        try {
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null ;
    }
    @RequestMapping("/safeExit")//安全退出
    public String safeExit(HttpSession session){
        session.removeAttribute("admin");
        return "redirect:/login/login.jsp";
    }


    //注册
    @RequestMapping("/registerAdmin")
    public String registerAdmin(Admin admin,HttpSession session,String enCode){
        String code = (String) session.getAttribute("code");
        if (code.equals(enCode)){
            adminService.insertAdmin(admin);
            return  "redirect:/login/login.jsp";
        }else {
            return  "redirect:/login/register.jsp";
        }

    }
    //发送的随机验证码
    @RequestMapping("/sendCode")
    public void sendCode(HttpSession session){
        adminService.sendMessage(session);
//        return "redirect:/login/register.jsp";
    }



    //根据用户名查找用户,用于表单验证
    @RequestMapping("/findByUsername")
    public String findByUsername(String username,HttpServletResponse response){
        Admin admin = adminService.findByUsername(username);
        try {
            PrintWriter out = response.getWriter();
            if (admin==null){
                out.print("Y");
            }else {
                out.print("N");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
