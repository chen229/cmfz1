package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.Maps;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    //分页查询全部的用户
    @RequestMapping("/findAllUser")
    @ResponseBody
    public Map<String,Object> findAllUser(Integer page,Integer rows) {
        Map<String, Object> map = userService.findAllUser(page, rows);
        return map;
    }


    //导出用户信息所需的方法
    @RequestMapping("/findAll")
    @ResponseBody
    public void findAll() throws Exception {



        List<User> userList = userService.findAll();

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户表详情","用户信息表"),
                User.class, userList);

        //写入本地磁盘
        workbook.write(new FileOutputStream(new File("g:/user.xls")));
    }


    //根据用户性别查询不同城市的数量
    @RequestMapping("/findBySex")
    @ResponseBody
//    public List<Object> findBySex(){
    public Map<String,Object> findBySex(){
        Map<String,Object> map=new HashMap<>();
//        List<Object> list=new ArrayList<>();
        List<Maps> femaleList = userService.findBySexAndPro("女");
        map.put("female",femaleList);
//        list.add(femaleList);
        List<Maps> maleList = userService.findBySexAndPro("男");
        map.put("male",maleList);
//        list.add(maleList);
//        return list;
        return map;
    }

    //根据性别查询用户注册的折线图
    @RequestMapping("/findByRegister")
    @ResponseBody
    public Map<String,Object> findByRegister(){
        Map<String,Object> map=new HashMap<>();
        //女性用户上半年注册的数据
        List<Integer> fList=new ArrayList<>();
        Integer f1 = userService.findBySex1("女");//一月份注册的人数
        Integer f12 = userService.findBySex2("女");
        Integer f2=f12-f1;//二月份注册的人数
        Integer f13 = userService.findBySex3("女");
        Integer f3=f13-f12;//三月份注册的人数
        Integer f14 = userService.findBySex4("女");
        Integer f4=f14-f13;//四月份注册的人数
        Integer f15 = userService.findBySex5("女");
        Integer f5=f15-f14;//五月份注册的人数
        Integer f16 = userService.findBySex6("女");
        Integer f6=f16-f15;//六月份注册的人数
        fList.add(f1);
        fList.add(f2);
        fList.add(f3);
        fList.add(f4);
        fList.add(f5);
        fList.add(f6);

        //男性用户上半年注册的数据
        List<Integer> mList=new ArrayList<>();
        Integer fm1 = userService.findBySex1("男");//一月份注册的人数
        Integer f22 = userService.findBySex2("男");
        Integer fm2=f22-f1;//二月份注册的人数
        Integer f33 = userService.findBySex3("男");
        Integer fm3=f33-f22;//三月份注册的人数
        Integer f44 = userService.findBySex4("男");
        Integer fm4=f44-f33;//四月份注册的人数
        Integer f55 = userService.findBySex5("男");
        Integer fm5=f55-f44;//五月份注册的人数
        Integer f66 = userService.findBySex6("男");
        Integer fm6=f66-f55;//六月份注册的人数
        mList.add(fm1);
        mList.add(fm2);
        mList.add(fm3);
        mList.add(fm4);
        mList.add(fm5);
        mList.add(fm6);
        map.put("fList",fList);
        map.put("mList",mList);

        System.out.println(fList);
        System.out.println(mList);

        return map;
    }

}
