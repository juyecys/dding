package cn.com.dingduoduo.api.wp.banner;

import cn.com.dingduoduo.api.common.ApiResult;
import cn.com.dingduoduo.entity.banner.BannerDTO;
import cn.com.dingduoduo.service.banner.BannerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
<<<<<<< Updated upstream
 * Created by jeysine on 2018/4/4.
=======
 * Created by jeysine on 2018/4/8.
>>>>>>> Stashed changes
 */
@RestController
@RequestMapping(value = { "/ding/wp/public/banner","/ding/wp/private/banner" }, produces = "application/json")
public class PublicWPBannerController {
    private static Logger logger = LoggerFactory.getLogger(PublicWPBannerController.class);

    @Autowired
    private BannerService bannerService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getBanner(BannerDTO banner) {
        List<BannerDTO> list = bannerService.findByCondition(banner);
        return new ResponseEntity<>(ApiResult.success(list), HttpStatus.OK);
    }
}
