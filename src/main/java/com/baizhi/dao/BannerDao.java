package com.baizhi.dao;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface BannerDao{
    //查询全部的信息
    List<Banner> selectAll();
    //分页查询
    List<Banner> selectByPage(
            @Param("currentPage") Integer currentPage,
            @Param("pageSize") Integer pageSize,
            @Param("sidx") String sidx,
            @Param("sord") String sord);
    //模糊查询
    List<Banner> selectByLike(
            @Param("name") String name,
            @Param("currentPage") Integer currentPage,
            @Param("pageSize") Integer pageSize,
            @Param("sidx") String sidx,
            @Param("sord") String sord);
    //查询总的信息条数
    Integer count();
    //添加轮播图
    void insertBanner(Banner banner);
    //根据id删除轮播图
    void deleteBanner(String id);
    //修改轮播图信息
    void updateBanner(Banner banner);
}
