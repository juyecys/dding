package cn.com.dingduoduo.service.banner;

import cn.com.dingduoduo.entity.banner.BannerDTO;
import cn.com.dingduoduo.entity.banner.BannerGroup;
import cn.com.dingduoduo.entity.banner.BannerGroupDTO;
import cn.com.dingduoduo.service.common.BaseService;

import java.util.List;

/**
 * Created by jeysine on 2018/4/2.
 */
public interface BannerGroupService extends BaseService<BannerGroup, BannerGroupDTO> {
    BannerGroup createOrUpdate(BannerGroup entity, List<BannerDTO> bannerList) throws Exception;
}
