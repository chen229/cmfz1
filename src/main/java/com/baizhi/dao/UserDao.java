package com.baizhi.dao;

import com.baizhi.entity.Maps;
import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User> {
    //根据性别和城市来查询信息的个数
    List<Maps> selectByProAndSex(String sex);







    //按性别查询每个月注册的男女人数
    Integer selectBySex1(String sex);
    //按性别查询每个月注册的男女人数
    Integer selectBySex2(String sex);
    //按性别查询每个月注册的男女人数
    Integer selectBySex3(String sex);
    //按性别查询每个月注册的男女人数
    Integer selectBySex4(String sex);
    //按性别查询每个月注册的男女人数
    Integer selectBySex5(String sex);
    //按性别查询每个月注册的男女人数
    Integer selectBySex6(String sex);

}
