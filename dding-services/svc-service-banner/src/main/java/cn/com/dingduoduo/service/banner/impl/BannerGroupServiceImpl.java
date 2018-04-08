package cn.com.dingduoduo.service.banner.impl;

import cn.com.dingduoduo.dao.banner.BannerGroupDAO;
import cn.com.dingduoduo.entity.banner.Banner;
import cn.com.dingduoduo.entity.banner.BannerDTO;
import cn.com.dingduoduo.entity.banner.BannerGroup;
import cn.com.dingduoduo.entity.banner.BannerGroupDTO;
import cn.com.dingduoduo.entity.common.Page;
import cn.com.dingduoduo.service.banner.BannerGroupService;
import cn.com.dingduoduo.service.banner.BannerService;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jeysine on 2018/4/2.
 */
@Service("bannerGroupService")
public class BannerGroupServiceImpl extends BaseServiceImpl<BannerGroup, BannerGroupDTO> implements BannerGroupService {

    @Autowired
    private BannerGroupDAO dao;

    @Autowired
    private BannerService bannerService;

    @Autowired
    public void setDao(BannerGroupDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }

    @Override
    public BannerGroup createOrUpdate(BannerGroup entity, List<BannerDTO> bannerList) throws Exception {
        if (entity.getId() == null) {
            create(entity);
        } else {
            update(entity);
        }
        bannerService.createOrUpdateBatch(entity.getId(), entity.getCode(), bannerList);
        return entity;
    }

    @Override
    public Page<BannerGroupDTO> findByConditionPage(BannerGroupDTO qm) {
        if (qm.getPage() == null) {
            qm.setPage(new Page(10));
        }
        List<BannerGroupDTO> list = dao.findByConditionPage(qm);
        if (list != null) {
            BannerDTO  banner = new BannerDTO();
            for (BannerGroupDTO bannerGroup: list) {
                banner.setBannerGroupId(bannerGroup.getId());
                List<BannerDTO> bannerList = bannerService.findByCondition(banner);
                bannerGroup.setBannerList(bannerList);
            }
        }
        qm.getPage().setResult(list);
        return qm.getPage();
    }
}
