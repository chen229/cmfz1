package com.baizhi.service;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import io.goeasy.GoEasy;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @Override//查询全部的文章
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map<String,Object> findAllArticle(Integer page, Integer rows) {
        Map<String,Object> map=new HashMap<>();
        Article article = new Article();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Article> articles = articleDao.selectByRowBounds(article, rowBounds);
        int count = articleDao.selectCount(article);
        map.put("page",page);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("rows",articles);
        map.put("records",count);
        return map;
    }

    @Override//删除文章
    public void removeArticle(String id) {
        Article article = new Article();
        article.setId(id);
        int i = articleDao.deleteByPrimaryKey(article);
        if (i==0){
            throw new RuntimeException("更新文章失败");
        }

    }

    @Override//更新文章
    public void modifyArticle(Article article) {
        int i = articleDao.updateByPrimaryKeySelective(article);
        if (i==0){
            throw new RuntimeException("更新文章失败");
        }
    }

    @Override//添加文章
    public void addArticle(Article article) {
        article.setId(UUID.randomUUID().toString());
        article.setCreateTime(new Date());
        int i = articleDao.insertSelective(article);
        if (i==0){
            throw new RuntimeException("更新文章失败");
        }else {
            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-872e282a2a424daf8e504d04f16b0a24");
            goEasy.publish("CmfzArticle", article.getContent());
        }

    }

    @Override//查询所有的文章
    public List<Article> findAll() {
        List<Article> articles = articleDao.selectAll();
        return articles;
    }

    @Override//根据用户id查询文章
    public List<Article> findByGuruId(String guruId) {
        Article article = new Article();
        article.setAuthor(guruId);
        List<Article> articleList = articleDao.select(article);
        return articleList;
    }
}
