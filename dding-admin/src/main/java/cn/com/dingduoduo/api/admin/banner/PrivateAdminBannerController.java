package cn.com.dingduoduo.api.admin.banner;

import cn.com.dingduoduo.api.admin.universal.PrivateAdminUniversalController;
import cn.com.dingduoduo.api.common.ApiResult;
import cn.com.dingduoduo.entity.banner.BannerGroupDTO;
import cn.com.dingduoduo.entity.common.Page;
import cn.com.dingduoduo.service.banner.BannerGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jeysine on 2018/3/29.
 */
@RestController
@RequestMapping(value = { "/ding/mg/private/banner","/ding/mg/public/banner" }, produces = "application/json")
public class PrivateAdminBannerController {

    @Autowired
    private BannerGroupService bannerGroupService;

    private Logger logger = LoggerFactory.getLogger(PrivateAdminUniversalController.class);

    @RequestMapping(value = "/group/page", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getPage(@RequestBody BannerGroupDTO bannerGroup) throws Exception {
        Page<BannerGroupDTO> page = bannerGroupService.findByConditionPage(bannerGroup);
        return new ResponseEntity<>(ApiResult.success(page), HttpStatus.OK);
    }
    @RequestMapping(value = "/group", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> createOrUpdate(@RequestBody BannerGroupDTO bannerGroup) throws Exception {
        bannerGroupService.createOrUpdate(bannerGroup, bannerGroup.getBannerList());
        return new ResponseEntity<>(ApiResult.success(bannerGroup), HttpStatus.OK);
    }
}
