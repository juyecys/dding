package cn.com.dingduoduo.service.wechat.menu;

import cn.com.dingduoduo.entity.wechat.menu.WechatMenu;
import cn.com.dingduoduo.entity.wechat.result.WechatCommonResult;

import java.io.IOException;

public interface WechatMenuService {
	Boolean createWechatMenu(WechatMenu wechatMenu) throws IOException;
	String getWechatMenu() throws IOException;
	WechatCommonResult deleteWechatMenu() throws IOException;
	Boolean generateWechatMenu() throws IOException;
}
