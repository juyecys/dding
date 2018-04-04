package cn.com.dingduoduo.service.banner.impl;

import cn.com.dingduoduo.dao.banner.BannerDAO;
import cn.com.dingduoduo.entity.banner.Banner;
import cn.com.dingduoduo.entity.banner.BannerDTO;
import cn.com.dingduoduo.service.banner.BannerService;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jeysine on 2018/3/29.
 */
@Service("bannerService")
public class BannerServiceImpl extends BaseServiceImpl<Banner, BannerDTO> implements BannerService{
    @Autowired
    private BannerDAO dao;

    @Autowired
    public void setDao(BannerDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }

    @Override
    public List<BannerDTO> createOrUpdateBatch(String bannerGroupId,String bannerGroupCode, List<BannerDTO> bannerList) throws Exception {
        for (BannerDTO banner: bannerList) {
            if (banner.getDelete() != null && banner.getDelete()) {
                deleteById(banner.getId());
            } else {
                banner.setBannerGroupId(bannerGroupId);
                banner.setBannerGroupCode(bannerGroupCode);
                createOrUpdate(banner);
            }
        }
        return bannerList;
    }
}
