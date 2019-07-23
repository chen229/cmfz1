package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumService {
    //查询所有的专辑
    Map<String,Object> findAllAlbum(Integer page,Integer rows);
    //添加专辑
    void addAlbum(Album album);
    //修改专辑
    void updateAlbum(Album album);
    List<Album> findAll();
}
