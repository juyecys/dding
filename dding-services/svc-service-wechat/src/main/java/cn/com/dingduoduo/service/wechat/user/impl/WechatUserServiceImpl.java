package cn.com.dingduoduo.service.wechat.user.impl;


import cn.com.dingduoduo.utils.common.okhttputil.OkHttpUtils;
import cn.com.dingduoduo.contants.wechat.WechatConfigParams;
import cn.com.dingduoduo.entity.wechat.acesstoken.WechatAccessToken;
import cn.com.dingduoduo.entity.wechat.user.WechatUser;
import cn.com.dingduoduo.entity.wechat.user.WechatUserDTO;
import cn.com.dingduoduo.service.wechat.accesstoken.WechatAccessTokenService;
import cn.com.dingduoduo.service.wechat.user.WechatUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service("wechatUserService")
public class WechatUserServiceImpl implements WechatUserService {
	@Autowired
	private WechatAccessTokenService wechatAccessTokenService;
	private Logger logger = LoggerFactory.getLogger(WechatUserServiceImpl.class);
	private static final ObjectMapper mapper = new ObjectMapper();
	@Override
	public WechatUser getWechatUserInfo(String openid, String lang) throws IOException {
		WechatAccessToken wechatAccessToken = wechatAccessTokenService.getAccessToken();
		String url = WechatConfigParams.WEHCAT_GET_USER_URL.replace("ACCESS_TOKEN", wechatAccessToken.getAccessToken())
				.replace("OPENID", openid);
		String result = OkHttpUtils.get().url(url).build().execute().body().string();

		return mapper.readValue(result, WechatUser.class);
	}

	@Override
	public WechatUserDTO getWechatUserInfoList(List<String> openids) throws IOException {
		if (!openids.isEmpty()) {
			List<WechatUser> wechatUserList = new LinkedList<>();
			for (String openid: openids) {
				WechatUser wechatUser = new WechatUser();
				wechatUser.setOpenId(openid);
				wechatUserList.add(wechatUser);
			}
			WechatAccessToken wechatAccessToken = wechatAccessTokenService.getAccessToken();
			String url = WechatConfigParams.WECHAT_GET_USER_LIST_URL.replace("ACCESS_TOKEN", wechatAccessToken.getAccessToken());
			Map<String, Object> map = new HashMap<>();
			map.put("user_list", wechatUserList);
			String properties = mapper.writeValueAsString(map);
			logger.info("pro: {}", properties);
			String result = OkHttpUtils.postString().url(url).content(properties).build().execute().body().string();

			//result = result.substring(18, result.length()).replaceAll("\\\\", "");
			logger.info("result: {}", result);
			return mapper.readValue(result, WechatUserDTO.class);
		}
		return  null;


	}

	@Override
	public WechatUser getWechatUserInfoByAuth(String authAccessToken, String openid) throws IOException {
		String url = WechatConfigParams.WECHAT_GET_USER_BY_AUTH_URL.replace("ACCESS_TOKEN", authAccessToken)
				.replace("OPENID", openid);
		String result = OkHttpUtils.get().url(url).build().execute().body().string();

		return mapper.readValue(result, WechatUser.class);
	}

	public static void main(String[] args) throws IOException {
		String url = WechatConfigParams.WECHAT_GET_USER_BY_AUTH_URL.replace("ACCESS_TOKEN", "8_hRo25BnZUEjOzPQchBB-a7Kmq1JhaEvK3zji0qidMsmdnPvlGC0lB2uy-EFigrTpUfVZ8e-pyGJ-1CCHL0zuTQ")
				.replace("OPENID", "oUo2Rs3VcJQo1z_6XDJ4RJz8kWZI");
		String result = OkHttpUtils.get().url(url).build().execute().body().string();
		System.out.println(result);
	}
}
