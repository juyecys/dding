package cn.com.dingduoduo.service.wechat.user;

import cn.com.dingduoduo.entity.wechat.user.WechatUser;
import cn.com.dingduoduo.entity.wechat.user.WechatUserDTO;

import java.io.IOException;
import java.util.List;

public interface WechatUserService {
	WechatUser getWechatUserInfo(String openid, String lang) throws IOException;
	WechatUserDTO getWechatUserInfoList(List<String> openids) throws IOException;
	WechatUser getWechatUserInfoByAuth(String authAccessToken, String openid) throws IOException;
}
