package cn.com.dingduoduo.api.admin.banner;

import cn.com.dingduoduo.api.admin.universal.PrivateAdminUniversalController;
import cn.com.dingduoduo.api.common.ApiResult;
import cn.com.dingduoduo.entity.banner.Banner;
import cn.com.dingduoduo.entity.common.Page;
import cn.com.dingduoduo.service.banner.BannerService;
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
    private BannerService bannerService;

    private Logger logger = LoggerFactory.getLogger(PrivateAdminUniversalController.class);

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getPage(@RequestBody Banner banner) throws Exception {
        Page<Banner> page = bannerService.findByConditionPage(banner);
        return new ResponseEntity<>(ApiResult.success(page), HttpStatus.OK);
    }
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> createOrUpdate(@RequestBody Banner banner) throws Exception {
        banner = bannerService.createOrUpdate(banner);
        return new ResponseEntity<>(ApiResult.success(banner), HttpStatus.OK);
    }
}
