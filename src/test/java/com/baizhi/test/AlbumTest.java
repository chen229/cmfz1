package com.baizhi.test;

import com.baizhi.Application;
import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AlbumTest {
    @Autowired
    private AlbumDao albumDao;


    @Test//查询全部的专辑
    public void test1(){
        Album album = new Album();
        RowBounds rowBounds = new RowBounds(0,3);
        List<Album> albums = albumDao.selectByRowBounds(album, rowBounds);
        for (Album album1 : albums) {
            System.out.println(album1);
        }
    }
    @Test
    public void test2(){
        Album album = new Album();
        int count = albumDao.selectCount(album);
        System.out.println("总共"+count+"条信息");
    }


}
