package com.baizhi.service;


import com.baizhi.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    //分页查询全部的文章
    Map<String,Object> findAllArticle(Integer page, Integer rows);
    //根据id删除文章
    void removeArticle(String id);
    //修改文章信息
    void modifyArticle(Article article);
    //添加文章
    void addArticle(Article article);
    //查询全部的文章
    List<Article> findAll();
    //根据上师id查询文章
    List<Article> findByGuruId(String guruId);
    //根据输入的内容查询文章
    List<Article> findByContent(String content);
}
