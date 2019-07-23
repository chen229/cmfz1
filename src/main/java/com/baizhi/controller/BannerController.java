package com.baizhi.controller;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private BannerDao bannerDao;



    //查询所有轮播图
    @RequestMapping("/findBy")
    @ResponseBody
    public Map<String,Object> findBy(Integer page,Integer rows,
                                             String sidx,String sord,Boolean _search,
                                             String name){
        //创建一个hashMap
        Map<String,Object> map=new HashMap<>();

        if(_search==true){//执行模糊查询操作
            Integer totalPage = bannerService.totalPage();//显示总的页数
            List<Banner> banners = bannerService.findByLike(name,page,rows,sidx,sord);
            map.put("page",page);
            map.put("total",totalPage);
            map.put("records",bannerDao.count());
            map.put("rows",banners);
        }else {//执行分页查询
           Integer total= bannerService.totalPage();//总的页数
            List<Banner> banners = bannerService.findByPage(page, rows, sidx, sord);
            map.put("page",page);
            map.put("total",total);
            map.put("records",bannerDao.count());
            map.put("rows",banners);
        }
        return map;
    }
    //写操作
    @RequestMapping("/editBanner")
    @ResponseBody
    public Map<String,String > editBanner(String id,Banner banner,String oper){
        Map<String,String> status=new HashMap<>();
        if ("add".equals(oper)){
            bannerService.addBanner(banner);
            status.put("id",banner.getId());
            status.put("status","ok");
        }else if("del".equals(oper)){
            bannerService.removeBanner(id);
            status.put("status","ok");
        }else if("edit".equals(oper)){
            if ("".equals(banner.getCover())){
                banner.setCover(null);
            }
            bannerService.modifyBanner(banner);
            status.put("status","ok");
        }else {
            status.put("status","error");
        }
        return status;
    }

    //文件上传
    @RequestMapping("/upLoad")
    @ResponseBody
    public void upLoad(String id, MultipartFile cover, HttpServletRequest request) throws IOException {
        System.out.println("*****大家呀");
        //获取真实的保存上传文件的路径
        String realPath = request.getServletContext().getRealPath("/image");
        //获得上传文件的名称
        String filename = cover.getOriginalFilename();
        //获得上传文件的类型
        String contentType = cover.getContentType();
        //文件上传
        cover.transferTo(new File(realPath+"/"+filename));

        System.out.println("+++"+id);
        System.out.println("****"+filename);
        //根据id修改图片的名字
        Banner banner =new Banner();
       banner.setId(id);
       banner.setCover(filename);
        bannerService.modifyBanner(banner);

    }
}
