package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;



    @Override//查询所有的专辑
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map<String,Object> findAllAlbum(Integer page,Integer rows) {
        Map<String,Object> map=new HashMap<>();
        Album album=new Album();
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        int count = albumDao.selectCount(album);
        List<Album> albums = albumDao.selectByRowBounds(album, rowBounds);
        map.put("page",page);//当前页数
        map.put("rows",albums);//查询到的数据
        map.put("total",count%rows==0?count/rows:count/rows+1);//总页数
        map.put("records",count);//总的信息条数
        return map;
    }

    @Override//添加专辑
    public void addAlbum(Album album) {
        String uuid = UUID.randomUUID().toString();
        Date date = new Date();
        album.setId(uuid);
        album.setCreateTime(date);



        album.setCount(0);
        int i = albumDao.insert(album);//返回值代表成功操作的个数
    }

    @Override//修改专辑
    public void updateAlbum(Album album) {
        int i = albumDao.updateByPrimaryKeySelective(album);

    }

    @Override//查询所有的专辑
    public List<Album> findAll() {
        List<Album> albums = albumDao.selectAll();
        return albums;
    }

}
