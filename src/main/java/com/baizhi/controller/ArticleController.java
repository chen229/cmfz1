package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    //分页查询全部的文章信息
    @RequestMapping("/findAllArticle")
    @ResponseBody
    public Map<String,Object> findAllArticle(Integer page,Integer rows){
        Map<String, Object> map = articleService.findAllArticle(page, rows);
        return map;
    }


    //写操作
    @RequestMapping("/editArticle")
    @ResponseBody
    public Map<String,Object> editArticle(String oper, Article article){
        Map<String,Object> map=new HashMap<>();
        if ("add".equals(oper)){
            System.out.println("&&&&&cheng工了吗");
            articleService.addArticle(article);
            map.put("status","ok");
        }else if("del".equals(oper)){
            articleService.removeArticle(article.getId());
            map.put("status","ok");
        }else if("edit".equals(oper)){
            articleService.modifyArticle(article);
            map.put("status","ok");
        } else {
            map.put("status","error");
        }
        return map;
    }

    //文件上传的路径
    @RequestMapping("/upload")
    @ResponseBody
    public Map<String,Object> upload(HttpServletRequest request, MultipartFile picName){
        Map<String,Object> map=new HashMap<>();
        try {
            //文件上传到的路径
            String realPath = request.getServletContext().getRealPath("/image");
            //文件的名字
            String filename = picName.getOriginalFilename();
            //文件的原始类型
            String contentType = picName.getContentType();
            //文件上传
            picName.transferTo(new File(realPath+"/"+filename));
            //返回插件所需要的内容
            map.put("error",0);
            map.put("url","http://localhost:8989/cmfz/image/"+filename);
        } catch (IOException e) {
            map.put("error",1);
        }
        return map;
    }
    //图片空间管理
    @RequestMapping("/browser")
    @ResponseBody
    public Map<String,Object> browser(HttpServletRequest request){
        //先获得存放图片的文件夹
        File file = new File(request.getSession().getServletContext().getRealPath("/image"));
        //获得文件夹里的内容
        File[] files = file.listFiles();

        //新建一个Map集合,用于返回插件所需要的内容
        Map<String,Object> map=new HashMap<>();
        //存入访问图片文件夹的路径
        map.put("current_url","http://localhost:8989/cmfz/image/");
        //当前文件夹中的图片数量
        map.put("total_count",files.length);
        //创建一个list集合,用于存放文件夹中的图片
        List<Object> list=new ArrayList<>();
        for (File img : files) {
//            创建一个map集合用于存放图片的属性信息
            Map<String,Object> map1 =new HashMap<>();
            map1.put("is_dir",false);
            map1.put("has_file",false);
            map1.put("fileSize",img.length());
            map1.put("is_photo",true);
            map1.put("fileType", FilenameUtils.getExtension(img.getName()));
            map1.put("filename",img.getName());
            map1.put("dateTime",new Date());
            list.add(map1);
        }
        //把得到的存放有图片信息的list集合放到map集合中
        map.put("file_list",list);
        return map;
     }
}
