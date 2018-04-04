package cn.com.dingduoduo.service.banner;

import cn.com.dingduoduo.entity.banner.Banner;
import cn.com.dingduoduo.entity.banner.BannerDTO;
import cn.com.dingduoduo.service.common.BaseService;

import java.util.List;

/**
 * Created by jeysine on 2018/3/29.
 */
public interface BannerService extends BaseService<Banner, BannerDTO>{
    List<BannerDTO> createOrUpdateBatch(String bannerGroupId,String bannerGroupCode, List<BannerDTO> bannerList) throws Exception;
}
