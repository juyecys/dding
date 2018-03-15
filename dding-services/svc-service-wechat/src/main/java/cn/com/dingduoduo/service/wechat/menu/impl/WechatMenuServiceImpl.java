package cn.com.dingduoduo.service.wechat.menu.impl;

import cn.com.dingduoduo.utils.common.okhttputil.OkHttpUtils;
import cn.com.dingduoduo.entity.wechat.acesstoken.WechatAccessToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.dingduoduo.contants.wechat.WechatConfigParams;
import cn.com.dingduoduo.contants.wechat.WechatReponseCodeEmnu;
import cn.com.dingduoduo.entity.wechat.localwechatmenu.LocalWechatMenu;
import cn.com.dingduoduo.entity.wechat.menu.WechatMenu;
import cn.com.dingduoduo.entity.wechat.result.WechatCommonResult;
import cn.com.dingduoduo.service.wechat.accesstoken.WechatAccessTokenService;
import cn.com.dingduoduo.service.wechat.localMenu.LocalWechatMenuService;
import cn.com.dingduoduo.service.wechat.menu.WechatMenuService;

import java.io.IOException;
import java.util.List;

@Service("wechatMenuService")
public class WechatMenuServiceImpl implements WechatMenuService {
	private static Logger logger = LoggerFactory.getLogger(WechatMenuServiceImpl.class);
	private static final ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private WechatAccessTokenService wechatAccessTokenService;

	@Autowired
	private LocalWechatMenuService localWechatMenuService;
	@Override
	public Boolean createWechatMenu(WechatMenu wechatMenu) throws IOException {
		WechatAccessToken accessToken = wechatAccessTokenService.getAccessToken();

		String url = WechatConfigParams.WECHAT_CREATE_MENU_URL.replace("ACCESS_TOKEN", accessToken.getAccessToken());

		String wechatMenuJson = mapper.writeValueAsString(wechatMenu);
		logger.info("send create menu request to cn.com.dingduoduo.api.admin.wechat :{}",wechatMenuJson);
		Response response = OkHttpUtils.postString().content(wechatMenuJson).url(url).build().execute();
		WechatCommonResult result = mapper.readValue(response.body().string(),WechatCommonResult.class);
		if (WechatReponseCodeEmnu.OK.getMsg().equals(result.getErrmsg())) {
			logger.info("create cn.com.dingduoduo.api.admin.wechat menu success,code:{},msg:{}", result.getErrcode(), result.getErrmsg());
			return true;
		} else {
			logger.error("create cn.com.dingduoduo.api.admin.wechat menu falid,errorcode:{},errormsg:{}", result.getErrcode(), result.getErrmsg());
		}
		return false;
	}

	@Override
	public String getWechatMenu() throws IOException {
		WechatAccessToken accessToken = wechatAccessTokenService.getAccessToken();
		String url = WechatConfigParams.WECHAT_GET_MENU_URL.replace("ACCESS_TOKEN", accessToken.getAccessToken());
		Response response = OkHttpUtils.get().url(url).build().execute();
		String result = response.body().string();
		logger.info("get cn.com.dingduoduo.api.admin.wechat menu result:{}",result);
		return result;
	}

	@Override
	public WechatCommonResult deleteWechatMenu() throws IOException {
		WechatAccessToken accessToken = wechatAccessTokenService.getAccessToken();
		String url = WechatConfigParams.WECHAT_DELETE_MENU_URL.replace("ACCESS_TOKEN", accessToken.getAccessToken());
		Response response = OkHttpUtils.get().url(url).build().execute();
		WechatCommonResult result = mapper.readValue(response.body().string(),WechatCommonResult.class);
		if (WechatReponseCodeEmnu.OK.getCode().equals(result.getErrcode())) {
			logger.info("delete cn.com.dingduoduo.api.admin.wechat menu success:{}",result);
		} else {
			logger.error("delete cn.com.dingduoduo.api.admin.wechat menu faild,errcode:{},errmsg:{}",result.getErrcode(),result.getErrmsg());
		}
		return result;
	}

	@Override
	public Boolean generateWechatMenu() throws IOException {
		WechatMenu wechatMenu = generateWechatMenuData();
		return createWechatMenu(wechatMenu);
	}

	private WechatMenu generateWechatMenuData() {
		LocalWechatMenu search = new LocalWechatMenu();
		search.setLevel(1);
		List<LocalWechatMenu> wechatParentMenuList =localWechatMenuService.findByCondition(search);

		if (wechatParentMenuList.isEmpty()) {
			return null;
		}

		WechatMenu wechatMenu = new WechatMenu();
		WechatMenu.WechatButton[] buttons = new WechatMenu.WechatButton[wechatParentMenuList.size()];
		for (int i = 0; i < wechatParentMenuList.size(); i++) {
			LocalWechatMenu menu = wechatParentMenuList.get(i);
			if (isViewButton(menu.getType())) {
				buttons[i] = getViewButton(menu);
			} else if (isClickButton(menu.getType())) {
				buttons[i] = getClickButton(menu);
			} else if (isParentButton(menu.getType())) {
				search.setLevel(2);
				search.setParentId(menu.getId());
				List<LocalWechatMenu> subMenuList = localWechatMenuService.findByCondition(search);
				WechatMenu.WechatButton[] subButtons = new WechatMenu.WechatButton[subMenuList.size()];
				for (int j = 0; j < subMenuList.size(); j++) {

					LocalWechatMenu subMenu = subMenuList.get(j);
					if (isViewButton(subMenu.getType())) {
						subButtons[j] = getViewButton(subMenu);
					} else if (isClickButton(subMenu.getType())) {
						subButtons[j] = getClickButton(subMenu);
					}
				}
				WechatMenu.WechatButton parentButton = wechatMenu.new WechatButton();
				parentButton.setName(menu.getName());
				parentButton.setSubButton(subButtons);
				buttons[i] = parentButton;
			}
		}

		wechatMenu.setButton(buttons);
		return wechatMenu;
	}


	private WechatMenu.WechatViewButton getViewButton(LocalWechatMenu menu){
		WechatMenu.WechatViewButton viewButton = new WechatMenu().new WechatViewButton();
		viewButton.setType("view");
		viewButton.setUrl(menu.getUrl());
		viewButton.setName(menu.getName());
		return viewButton;
	}
	private WechatMenu.WechatClickButton getClickButton(LocalWechatMenu menu) {
		WechatMenu.WechatClickButton clickButton = new WechatMenu().new WechatClickButton();
		clickButton.setKey(menu.getKey());
		clickButton.setType("click");
		clickButton.setName(menu.getName());
		return clickButton;
	}

	private boolean isViewButton(String type) {
		return LocalWechatMenu.WechatMenuTypeEnum.VIEW.getValue().equals(type);
	}

	private boolean isClickButton(String type) {
		return LocalWechatMenu.WechatMenuTypeEnum.ARTICLE.getValue().equals(type)
				|| LocalWechatMenu.WechatMenuTypeEnum.TEXT.getValue().equals(type)
				|| LocalWechatMenu.WechatMenuTypeEnum.CLICK.getValue().equals(type);
	}

	private boolean isParentButton(String type) {
		return LocalWechatMenu.WechatMenuTypeEnum.GROUP.getValue().equals(type);
	}

}
