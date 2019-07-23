package com.baizhi.test;

import com.baizhi.Application;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ChapterTest {
    @Autowired
    private ChapterDao chapterDao;

    @Test//测试查询根据专辑id
    public void test1(){
        Chapter chapter = new Chapter();
        chapter.setAlbumId("2");
        RowBounds rowBounds = new RowBounds(0, 3);
        List<Chapter> chapters = chapterDao.selectByRowBounds(chapter, rowBounds);
        for (Chapter chapter1 : chapters) {
            System.out.println(chapter1);
        }
    }

    //测试添加章节
//    @Test
//    public void test2(){
//        Chapter chapter = new Chapter();
//        chapter.setCid(UUID.randomUUID().toString());
//        int i = chapterDao.insert(chapter);
//        System.out.println(
//                "成功插入"+i+"条数据"
//        );
//    }
}
