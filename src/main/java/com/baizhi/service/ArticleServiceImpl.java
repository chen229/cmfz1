package com.baizhi.service;

import com.baizhi.annotion.RedisCache;
import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.repository.ArticleRepository;
import io.goeasy.GoEasy;
import org.apache.commons.collections4.IterableUtils;
import org.apache.ibatis.session.RowBounds;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
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
    @RedisCache
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
        articleRepository.deleteById(id);
        if (i==0){
            throw new RuntimeException("删除文章失败");
        }

    }

    @Override//更新文章
    public void modifyArticle(Article article) {
        int i = articleDao.updateByPrimaryKeySelective(article);
        //更改索引库里的信息
        //先根据id查找文章
        Article primaryArticle = articleDao.selectByPrimaryKey(article.getId());
        primaryArticle.setTitle(article.getTitle());
        primaryArticle.setAuthor(article.getAuthor());
        primaryArticle.setCreateTime(article.getCreateTime());
        primaryArticle.setContent(article.getContent());
        primaryArticle.setUid(article.getUid());
        articleRepository.save(primaryArticle);

        if (i==0){
            throw new RuntimeException("更新文章失败");
        }
    }

    @Override//添加文章
    public void addArticle(Article article) {
        article.setId(UUID.randomUUID().toString());
        article.setCreateTime(new Date());
        //添加到数据库
        int i = articleDao.insertSelective(article);
        //添加到索引库
        articleRepository.save(article);
        if (i==0){
            throw new RuntimeException("添加文章失败");
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

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public List<Article> findByContent(String content) {
        //es代码
        if ("".equals(content) || content==null){//用户没有输入内容,那么系统默认查询所有并展示
            Iterable<Article> articles = articleRepository.findAll();
                        //转换为list集合
            List<Article> articleList = IterableUtils.toList(articles);
            return articleList;

        }else {//根据输入的内容查找
            HighlightBuilder.Field highlightBuilder = new HighlightBuilder
                    .Field("*")
                    .preTags("<span style='color:red'>")
                    .postTags("</span>")
                    .requireFieldMatch(false);

            NativeSearchQuery query = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.queryStringQuery(content).field("title").field("author").field("content"))
                    .withSort(SortBuilders.scoreSort())
                    .withHighlightFields(highlightBuilder)
                    .build();


            AggregatedPage<Article> articles = elasticsearchTemplate.queryForPage(query, Article.class, new SearchResultMapper() {
                @Override
                public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {
                    SearchHits hits = response.getHits();
                    List<Article> list = new ArrayList<>();
                    for (SearchHit hit : hits) {
                        Article article = new Article();
                        Map<String, Object> map = hit.getSourceAsMap();
                        article.setId(map.get("id").toString());
                        article.setAuthor(map.get("author").toString());
                        article.setTitle(map.get("title").toString());
                        article.setContent(map.get("content").toString());
                        String date = map.get("createTime").toString();
                        article.setCreateTime(new Date(Long.valueOf(date)));

                        //高亮
                        Map<String, HighlightField> fieldMap = hit.getHighlightFields();
                        if (fieldMap.get("title") != null) {
                            article.setTitle(fieldMap.get("title").getFragments()[0].toString());
                        }
                        if (fieldMap.get("author") != null) {
                            article.setAuthor(fieldMap.get("author").getFragments()[0].toString());
                        }
                        if (fieldMap.get("content") != null) {
                            article.setContent(fieldMap.get("content").getFragments()[0].toString());
                        }
                        list.add(article);

                    }
                    return new AggregatedPageImpl<T>((List<T>) list);
                }
            });
            List<Article> list = articles.getContent();
            return list;
        }
    }
}
