package com.baizhi.service;

import com.baizhi.entity.Maps;
import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    //分页查询全部的用户
    Map<String,Object> findAllUser(Integer page,Integer rows);
    //查询全部用户
    List<User> findAll();
    //根据用户id查询用户
    User findById(String id);
    //根据性别查询不同的省份的人数
    List<Maps> findBySexAndPro(String sex);
    //按性别查询每个月注册的男女人数
    Integer findBySex1(String sex);
    //按性别查询每个月注册的男女人数
    Integer findBySex2(String sex);
    //按性别查询每个月注册的男女人数
    Integer findBySex3(String sex);
    //按性别查询每个月注册的男女人数
    Integer findBySex4(String sex);
    //按性别查询每个月注册的男女人数
    Integer findBySex5(String sex);
    //按性别查询每个月注册的男女人数
    Integer findBySex6(String sex);
}
