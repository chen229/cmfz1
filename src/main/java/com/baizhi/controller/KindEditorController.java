package com.baizhi.controller;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/kindEditor")
public class KindEditorController {
    //图片上传的方法
    @RequestMapping("/upload")
    @ResponseBody
    public Map<String,Object> upLoad(MultipartFile picName, HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        try {
            //获取文件上传需要保存的路径
            String realPath = request.getServletContext().getRealPath("/image");
//            String realPath = request.getSession().getServletContext().getRealPath("image");
            //获取文件的原始类型
            String contentType = picName.getContentType();
            //获取文件的原始名字
            String filename = picName.getOriginalFilename();
            //文件上传
            picName.transferTo(new File(realPath+"/"+filename));
            //返回kindEditor插件需要的内容 -----一个存有error和url的map集合
            map.put("error",0);
            map.put("url","http://localhost:8989/cmfz/image/"+filename);
            System.out.println("****"+filename);
        } catch (IOException e) {
            map.put("error",1);
        }
        return map;
    }
    //图片空间管理r
    @RequestMapping("/browser")
    public Map<String,Object> browser(HttpServletRequest request){
        //获得存放图片的文件夹
        File file = new File(request.getSession().getServletContext().getRealPath("/image"));
        //获得文件夹里的文件
        File[] files = file.listFiles();

        //创建出一个kindEditor需要的返回值
        Map<String,Object> map=new HashMap<String, Object>();
        //当前文件夹的网络路径
        map.put("current_url","http://localhost:8989/cmfz/image/");
        //当前文件夹中的文件数量
        map.put("total_count",files.length);
        List<Object> list=new ArrayList<>();
        for (File img : files) {
            Map<String,Object> map1=new HashMap<>();
            map1.put("is_dir",false);
            map1.put("has_file",false);
            map1.put("fileSize",img.length());
            map1.put("is_photo",true);
            map1.put("fileType", FilenameUtils.getExtension(img.getName()));
            map1.put("filename", img.getName());
            map1.put("dateTime", "2018-09-09 00:36:39");
            list.add(map1);

        }
        map.put("file_list",list);
        return map;
    }
}
