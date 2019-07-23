package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import it.sauronsoftware.jave.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    //根据专辑id查询章节并分页
    @RequestMapping("/findByAlbumId")
    @ResponseBody
    public Map<String,Object> findByAlbumId(String albumId,Integer page,Integer rows){
        Map<String, Object> map = chapterService.findByAlbumId(albumId, page, rows);
        return map;
    }
    //添加章节
    @RequestMapping("/addChapter")
    @ResponseBody
    public Map<String,String> addChapter(String oper, Chapter chapter,String albumId){
        Map<String, String> map = new HashMap<>();
        if("add".equals(oper)){
            chapterService.addChapter(chapter,albumId);
            map.put("status","ok");
            map.put("id",chapter.getCid());
        }else if("edit".equals(oper)) {
            if ("".equals(chapter.getName())){
                chapter.setName(null);
            }
            chapterService.modifyChapter(chapter);
            map.put("status","ok");
        } else {
            map.put("status","error");
        }
        return map;
    }


    //上传文件
    @RequestMapping("/upLoad")
    @ResponseBody
    public void upLoad(MultipartFile name,String cid, HttpServletRequest request) throws Exception {
        //获得文件上传的绝对路径
        String realPath = request.getServletContext().getRealPath("/album/music");
        //获得上传文件的原始名称
        String filename = name.getOriginalFilename();
        //获得上传文件的真实类型
        String contentType = name.getContentType();
        //文件上传
        File file = new File(realPath + "/" + filename);//要上传的文件
        name.transferTo(file);


        //修改文件的名称
        Chapter chapter = new Chapter();
        chapter.setCid(cid);
        chapter.setName(filename);
        //获取文件的大小
        long nameSize = name.getSize();
        //保证精度,转换为BigDecimal的形式
        BigDecimal size = new BigDecimal(nameSize);
        //因为BigDecimal只能进行同类型的数据计算,所以把转换的数据也写成BigDecimal的形式
        BigDecimal dom = new BigDecimal(1024);
        //转换为大小为MB的形式
        BigDecimal scale = size.divide(dom).divide(dom)
                .setScale(2, BigDecimal.ROUND_HALF_UP);//四舍五入
        //由long的数据转换为字符串类型的,并set到章节里
        chapter.setSize(scale+"MB");
        //获取文件的时长
        Encoder encoder = new Encoder();
        long duration = encoder.getInfo(file).getDuration();
        //获得以时分秒的样式展现的时间,且是String类型的
        String duration1 = duration / 1000 / 60 + ":" + duration / 1000 % 60;
        chapter.setDuration(duration1);

        chapterService.modifyChapter(chapter);

    }

}
