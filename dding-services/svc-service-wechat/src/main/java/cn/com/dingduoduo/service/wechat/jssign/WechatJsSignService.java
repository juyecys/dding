package cn.com.dingduoduo.service.wechat.jssign;

import cn.com.dingduoduo.entity.wechat.jssign.WechatJsSign;

/**
 * Created by jeysine on 2018/3/26.
 */
public interface WechatJsSignService {
    WechatJsSign signUrl(String url) throws Exception;
}
