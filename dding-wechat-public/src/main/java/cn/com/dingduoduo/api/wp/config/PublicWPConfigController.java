package cn.com.dingduoduo.api.wp.config;

import cn.com.dingduoduo.api.common.ApiResult;
import cn.com.dingduoduo.entity.wechat.jssign.WechatJsSign;
import cn.com.dingduoduo.service.wechat.jssign.WechatJsSignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jeysine on 2018/3/26.
 */
@RestController
@RequestMapping(value = { "/ding/wp/public/config","/ding/wp/private/config" }, produces = "application/json")
public class PublicWPConfigController {
    private static Logger logger = LoggerFactory.getLogger(PublicWPConfigController.class);

    @Autowired
    private WechatJsSignService wechatJsSignServiceRPC;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getConfig(@RequestParam("url") String url) throws Exception {
        WechatJsSign sign = wechatJsSignServiceRPC.signUrl(url);
        return new ResponseEntity<>(ApiResult.success(sign), HttpStatus.OK);
    }

}
