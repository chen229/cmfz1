package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Article;
import com.baizhi.entity.Banner;
import com.baizhi.entity.User;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ArticleService;
import com.baizhi.service.BannerService;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/firstPage")
@RestController
public class FirstPageController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;

    //首页的接口方法
    @RequestMapping("/firstPage")
    public Map<String,Object> firstPage(String uid,String type,String sub_type){
        Map<String,Object> map=new HashMap<>();
        if ("all".equals(type)){//首页的内容
            //查询轮播图
            List<Banner> bannerList = bannerService.findAllBanner();
            //把查询到的结果存入到map集合里
            map.put("header",bannerList);

            //查询专辑
            List<Album> albumList = albumService.findAll();
            map.put("album",albumList);

            //根据用户id的对应的自己老师的文章
//            List<Article> articleList = articleService.findByUid(uid);

            //根据用户id查询出当前用户
            User user = userService.findById(uid);
            //获得对应上师的id
            String guruId = user.getGuruId();
            //根据对应的上师id查询其名下的文章
            List<Article> articleList = articleService.findByGuruId(guruId);
            map.put("article",articleList);
        }
        if ("wen".equals(type)){//闻的内容

        }
        if("si".equals(type)){//思的内容
            if("ssyj".equals(sub_type)){
                //查询对应老师的文章
            }
            if ("xmfy".equals(sub_type)){
                //查询所有的文章
            }
        }
        return map;
    }
}
