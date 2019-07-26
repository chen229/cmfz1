package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/es")
public class EsController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("/esArticle")
    public List<Article> esArticle(String content){
        List<Article> articleList = articleService.findByContent(content);
        for (Article article : articleList) {
            System.out.println("article"+article);
        }

        return articleList;
    }

}
