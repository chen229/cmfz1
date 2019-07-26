package com.baizhi.service;

import com.baizhi.annotion.RedisCache;
import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;


    //查找所有轮播图
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)

    public List<Banner> findAllBanner() {
        List<Banner> banners = bannerDao.selectAll();
        return banners;
    }

    @Override//分页查询
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @RedisCache
    public List<Banner> findByPage(Integer currentPage, Integer pageSize, String sidx, String sord) {
        currentPage=(currentPage-1)*pageSize;
        List<Banner> banners = bannerDao.selectByPage(currentPage, pageSize, sidx, sord);
//        System.out.println(currentPage);
//        System.out.println(pageSize);
//        System.out.println(sidx);
//        System.out.println(sord);
        return banners;
    }

    @Override//模糊查询
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Banner> findByLike(String name,Integer currentPage,
                                   Integer pageSize,
                                   String sidx,
                                   String sord) {
        currentPage=(currentPage-1)*pageSize;
        List<Banner> banners = bannerDao.selectByLike(name,currentPage,pageSize,sidx,sord);
        return banners;
    }

    @Override//查询总的信息条数
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Integer totalPage() {
        Integer totalPage;
        Integer count = bannerDao.count();
        if(count%3==0){//3为每页显示的信息条数
            totalPage=count/3;
        }else {
            totalPage=count/3+1;
        }
        return totalPage;
    }

    @Override//添加轮播图
    public void addBanner(Banner banner) {
        String uuid = UUID.randomUUID().toString();
        Date date = new Date();
        banner.setCreatetime(date);
        banner.setId(uuid);
        bannerDao.insertBanner(banner);
    }

    @Override//删除轮播图
    public void removeBanner(String id) {
        bannerDao.deleteBanner(id);
    }

    @Override//修改轮播图
    public void modifyBanner(Banner banner) {
        bannerDao.updateBanner(banner);
    }
}
