package cn.com.dingduoduo.service.wechat.callbackip;


import cn.com.dingduoduo.entity.wechat.acesstoken.WechatAccessToken;
import cn.com.dingduoduo.entity.wechat.callbackip.WechatCallBackIp;

import java.io.IOException;

/**
 * Created by jeysine on 2018/2/27.
 */
public interface WechatCallBackIpService {
    WechatCallBackIp getWechatCallBackIp(WechatAccessToken wechatAccessToken) throws IOException;
}
