package com.baizhi.test;

import com.baizhi.Application;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class ArticleTest {

    @Autowired
    private ArticleService articleService;
    @Test
    public void test1(){
//        articleService.addArticle(new Article(null,"hahah",null,null,null));
        System.out.println("哈哈哈");
    }

}
