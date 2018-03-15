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

	public static void main(String[] args) {
		String result = "{\\\"user_info_list\\\":[{\\\"subscribe\\\":1,\\\"openid\\\":\\\"opqVDwnsNk10ZGqqhR3d6AiCLTPI\\\",\\\"nickname\\\":\\\"liangjf\\\",\\\"sex\\\":1,\\\"language\\\":\\\"zh_CN\\\",\\\"city\\\":\\\"广州\\\",\\\"province\\\":\\\"广东\\\",\\\"country\\\":\\\"中国\\\",\\\"headimgurl\\\":\\\"http:\\/\\/thirdwx.qlogo.cn\\/mmopen\\/DY9yAjlJaL7C63SboE70ll1edbZ1zQeHnZJuIvshsLt3HcgTuZao3vgzoHibTDa06xRDibUUsbj0o3KJh4ibe2LpKvRIwwkDiaI3\\/132\\\",\\\"subscribe_time\\\":1517193407,\\\"unionid\\\":\\\"opuCTs2h3mOBp79l-DsWbHKNGmHw\\\",\\\"remark\\\":\\\"\\\",\\\"groupid\\\":0,\\\"tagid_list\\\":[]},{\\\"subscribe\\\":1,\\\"openid\\\":\\\"opqVDwrWpGfjwW0tTuqpk38rS-hc\\\",\\\"nickname\\\":\\\"Null\\\",\\\"sex\\\":1,\\\"language\\\":\\\"zh_CN\\\",\\\"city\\\":\\\"汕头\\\",\\\"province\\\":\\\"广东\\\",\\\"country\\\":\\\"中国\\\",\\\"headimgurl\\\":\\\"http:\\/\\/thirdwx.qlogo.cn\\/mmopen\\/DY9yAjlJaL6wevVCYVxwKiaECagsw5p7BRutXhPW6rpwYlVJb1XvgYSYPTdtQ9Yd5N5FiaIg4rF9Swkoy3C9sp1HcOy2CmdaUic\\/132\\\",\\\"subscribe_time\\\":1517469543,\\\"unionid\\\":\\\"opuCTszT0o5rSO2E0OKRYDCFRrGY\\\",\\\"remark\\\":\\\"\\\",\\\"groupid\\\":0,\\\"tagid_list\\\":[]},{\\\"subscribe\\\":1,\\\"openid\\\":\\\"opqVDwrWpGfjwW0tTuqpk38rS-hc\\\",\\\"nickname\\\":\\\"Null\\\",\\\"sex\\\":1,\\\"language\\\":\\\"zh_CN\\\",\\\"city\\\":\\\"汕头\\\",\\\"province\\\":\\\"广东\\\",\\\"country\\\":\\\"中国\\\",\\\"headimgurl\\\":\\\"http:\\/\\/thirdwx.qlogo.cn\\/mmopen\\/DY9yAjlJaL6wevVCYVxwKiaECagsw5p7BRutXhPW6rpwYlVJb1XvgYSYPTdtQ9Yd5N5FiaIg4rF9Swkoy3C9sp1HcOy2CmdaUic\\/132\\\",\\\"subscribe_time\\\":1517469543,\\\"unionid\\\":\\\"opuCTszT0o5rSO2E0OKRYDCFRrGY\\\",\\\"remark\\\":\\\"\\\",\\\"groupid\\\":0,\\\"tagid_list\\\":[]},{\\\"subscribe\\\":1,\\\"openid\\\":\\\"opqVDwj-hdL6ZzBfFHeSUjGPGajc\\\",\\\"nickname\\\":\\\"希望天空是蓝的\\\",\\\"sex\\\":0,\\\"language\\\":\\\"zh_CN\\\",\\\"city\\\":\\\"\\\",\\\"province\\\":\\\"\\\",\\\"country\\\":\\\"\\\",\\\"headimgurl\\\":\\\"http:\\/\\/thirdwx.qlogo.cn\\/mmopen\\/DY9yAjlJaL7wKAYiaqsUQMMB0LSGQ82AicGX1nN7bC1HMk8icJkeLSU11wGWxzqbD3SaRkkkQl5mTv2P6ULKTX2libj3sOGLiakHh\\/132\\\",\\\"subscribe_time\\\":1518163530,\\\"unionid\\\":\\\"opuCTsweyjwGJUXUK0laXaPpNnBs\\\",\\\"remark\\\":\\\"\\\",\\\"groupid\\\":0,\\\"tagid_list\\\":[]}]}";
		result = result.replaceAll("\\\\", "");
		try {
			System.out.println(result);
			WechatUserDTO list = mapper.readValue(result,
					WechatUserDTO.class);
			System.out.println(list);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
