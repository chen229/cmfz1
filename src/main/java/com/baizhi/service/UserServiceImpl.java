package com.baizhi.service;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.Maps;
import com.baizhi.entity.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;



    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override//查询全部的用户
    public Map<String, Object> findAllUser(Integer page, Integer rows) {
        Map<String,Object> map=new HashMap<>();
        User user = new User();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<User> users = userDao.selectByRowBounds(user, rowBounds);
        int count = userDao.selectCount(user);
        map.put("page",page);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("rows",users);
        map.put("records",count);
        return map;
    }

    @Override//查询全部用户
    public List<User> findAll() {
        User user = new User();
        List<User> users = userDao.select(user);
        return users;
    }

    @Override//根据用户id查询用户
    public User findById(String id) {
        User user = new User();
        user.setId(id);
        User user1 = userDao.selectOne(user);
        return user1;
    }

    @Override//根据性别查询不同省份的人数
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Maps> findBySexAndPro(String sex) {
        List<Maps> map = userDao.selectByProAndSex(sex);
        return map;
    }

    @Override
    public Integer findBySex1(String sex) {
        Integer count1 = userDao.selectBySex1(sex);
        return count1;
    }

    @Override
    public Integer findBySex2(String sex) {
        Integer count = userDao.selectBySex2(sex);
        return count;
    }

    @Override
    public Integer findBySex3(String sex) {
        Integer count = userDao.selectBySex3(sex);
        return count;
    }

    @Override
    public Integer findBySex4(String sex) {
        Integer count = userDao.selectBySex4(sex);
        return count;
    }

    @Override
    public Integer findBySex5(String sex) {
        Integer count = userDao.selectBySex5(sex);
        return count;
    }

    @Override
    public Integer findBySex6(String sex) {
        Integer count = userDao.selectBySex6(sex);
        return count;
    }
}
