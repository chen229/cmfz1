package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    //分页展示全部的信息
    @RequestMapping("/findAllAlbum")
    @ResponseBody
    public Map<String,Object> findAllAlbum(Integer page,Integer rows){
        Map<String, Object> map = albumService.findAllAlbum(page, rows);
        return map;
    }

    //添加专辑
    @RequestMapping("/addAlbum")
    @ResponseBody
    public Map<String,String> addAlbum(Album album,String oper){
        HashMap<String,String> map = new HashMap<>();
        if ("add".equals(oper)){
            albumService.addAlbum(album);
            map.put("status","ok");
            map.put("id",album.getId());
        }else if ("edit".equals(oper)) {
            if ("".equals(album.getCover())){
                album.setCover(null);
            }
            albumService.updateAlbum(album);
            map.put("status","ok");
        }else {
            map.put("status","error");
        }
        return map;
    }

    //文件上传
    @RequestMapping("/upLoad")
    @ResponseBody
    public void upLoad(String id, MultipartFile cover, HttpServletRequest request) throws IOException {
        //获得文件上传的真实路径
        String realPath = request.getServletContext().getRealPath("/image");
        //获得文件的原始名称
        String filename = cover.getOriginalFilename();
        //获得文件的原始类型
        String contentType = cover.getContentType();
        //文件上传
        cover.transferTo(new File(realPath+"/"+filename));

        System.out.println("获得的"+id);
        System.out.println(
                "获得的"+filename
        );
        //修改文件的图片路径
        Album album = new Album();
        album.setId(id);
        album.setCover(filename);
        albumService.updateAlbum(album);

    }


}
