package cn.com.dingduoduo.service.wechat.callbackip.impl;

import cn.com.dingduoduo.utils.common.okhttputil.OkHttpUtils;
import cn.com.dingduoduo.entity.wechat.acesstoken.WechatAccessToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import cn.com.dingduoduo.contants.wechat.WechatConfigParams;
import cn.com.dingduoduo.entity.wechat.callbackip.WechatCallBackIp;
import cn.com.dingduoduo.service.wechat.callbackip.WechatCallBackIpService;
import cn.com.dingduoduo.service.wechat.user.impl.WechatUserServiceImpl;

import java.io.IOException;

/**
 * Created by jeysine on 2018/2/27.
 */
@Service("wechatCallBackIpService")
public class WechatCallBackIpServiceImpl implements WechatCallBackIpService {

    private Logger logger = LoggerFactory.getLogger(WechatUserServiceImpl.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public WechatCallBackIp getWechatCallBackIp(WechatAccessToken wechatAccessToken) throws IOException {
        String url = WechatConfigParams.WECHAT_GET_CALL_BACK_IP.replace("ACCESS_TOKEN", wechatAccessToken.getAccessToken());
        String result = OkHttpUtils.get().url(url).build().execute().body().string();
        logger.debug("get cn.com.dingduoduo.api.admin.wechat call back ip result: {}", result);
        return mapper.readValue(result, WechatCallBackIp.class);
    }
}
