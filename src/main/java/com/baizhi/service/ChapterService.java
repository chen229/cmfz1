package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.List;
import java.util.Map;

public interface ChapterService {
    //根据专辑id查询章节
    Map<String,Object> findByAlbumId(String albumId,Integer page,Integer rows);
    //添加章节
    void addChapter(Chapter chapter,String albumId);
    //更新章节
    void modifyChapter(Chapter chapter);
}
