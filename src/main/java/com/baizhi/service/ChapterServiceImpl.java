package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;

    @Override//分页根据专辑id查找章节
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map<String,Object> findByAlbumId(String albumId,Integer page,Integer rows) {
        Map<String,Object> map=new HashMap<>();
        Chapter chapter = new Chapter();
        chapter.setAlbumId(albumId);
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Chapter> chapters = chapterDao.selectByRowBounds(chapter, rowBounds);
        int count = chapterDao.selectCount(chapter);
        map.put("page",page);
        map.put("rows",chapters);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override//添加章节数
    public void addChapter(Chapter chapter,String albumId) {
        chapter.setCid(UUID.randomUUID().toString());
        chapter.setCreateTime(new Date());
        chapter.setAlbumId(albumId);


        int i = chapterDao.insert(chapter);
        if(i==0){
            throw new RuntimeException("添加章节失败");
        }
    }

    @Override//更新章节
    public void modifyChapter(Chapter chapter) {
        int i = chapterDao.updateByPrimaryKeySelective(chapter);
        if (i==0){
            throw new RuntimeException("修改章节失败");
        }
    }
}
