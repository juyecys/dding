package cn.com.dingduoduo.service.banner.impl;

import cn.com.dingduoduo.dao.banner.BannerDAO;
import cn.com.dingduoduo.entity.banner.Banner;
import cn.com.dingduoduo.service.banner.BannerService;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jeysine on 2018/3/29.
 */
@Service("bannerService")
public class BannerServiceImpl extends BaseServiceImpl<Banner, Banner> implements BannerService{
    @Autowired
    private BannerDAO dao;

    @Autowired
    public void setDao(BannerDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }
}
