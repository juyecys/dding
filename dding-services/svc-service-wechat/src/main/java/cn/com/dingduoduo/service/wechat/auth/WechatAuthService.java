package cn.com.dingduoduo.service.wechat.auth;


import cn.com.dingduoduo.entity.wechat.auth.WechatAuthAccessToken;

import java.io.IOException;

/**
 * Created by jeysine on 2018/2/8.
 */
public interface WechatAuthService {

    WechatAuthAccessToken getAuthAccessTokenByCode(String code) throws IOException;

    WechatAuthAccessToken getAccessTokenByRefreshToken(String refreshToken) throws IOException;
}
