package com.baizhi.service;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerService {
    //查找所有轮播图图片
    List<Banner> findAllBanner();

    //分页查询
    List<Banner> findByPage(
            Integer currentPage,
            Integer pageSize,
             String sidx,
             String sord);
    //模糊查询
    List<Banner> findByLike(String name,
                            Integer currentPage,
                            Integer pageSize,
                            String sidx,
                            String sord);
    //查询总的信息条数
    Integer totalPage();
    //添加轮播图
    void addBanner(Banner banner);
    //根据id删除轮播图
    void removeBanner(String id);
    //修改轮播图信息
    void modifyBanner(Banner banner);
}
