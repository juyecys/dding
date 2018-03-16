package cn.com.dingduoduo.contants.wechat;

public class WechatConfigParams {
	// access token 在redis中的键
	public static final String ACCESS_TOKEN_KEY = "dding_access_token";

	//从微信获取access token需要的分布式锁key
	public static final String ACCESS_TOKEN_KEY_MUTEX = ACCESS_TOKEN_KEY + "_mutex";

	public static final String WECHAT_TEMPLATE_FILE_DIR = "/wechat/";

	// 扫描二维码EventKey前缀
	public static final String WECHAT_PREFIX_QRCODE_EVENT_KEY = "qrscene_";

	/**
	 * 用户授权
	 */
	public static final String WECHAT_GET_AUTH_ACCESSTOKEN_URL="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	public static final String WECHAT_REFRESH_AUTH_ACCESSTOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";

	/**
	 * 微信accesToken获取
	 */
	public static final String WECHAT_GET_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	/**
	 * 微信菜单Url
	 */
	public static final String WECHAT_CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	public static final String WECHAT_GET_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	public static final String WECHAT_DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

	/**
	 * 微信获取用户信息url
	 */
	public static final String WEHCAT_GET_USER_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	public static final String WECHAT_GET_USER_LIST_URL  = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";
	public static final String WECHAT_GET_USER_BY_AUTH_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	/**
	 * 微信客服管理
	 */
	public static final String WEHCAT_ADD_CUSTOM_URL = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN";
	public static final String WECHAT_UPDATE_CUSTOM_URL = "https://api.weixin.qq.com/customservice/kfaccount/update?access_token=ACCESS_TOKEN";
	public static final String WEHCAT_DELETE_CUSTOM_URL = "https://api.weixin.qq.com/customservice/kfaccount/del?access_token=ACCESS_TOKEN";
	public static final String WEHCAT_UPDATE_CUSTOM_HEAD_PIC_URL = "http://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token=ACCESS_TOKEN&kf_account=KFACCOUNT";
	public static final String WEHCAT_GET_CUSTOM_LIST_URL = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=ACCESS_TOKEN";
	public static final String WECHAT_CUSTOM_MESSAGE = "https://api.weixin.qq.com/cgi-bin/cn.com.dingduoduo.api.admin.adminuser.message/custom/send?access_token=ACCESS_TOKEN";

	public static final String WEHCAT_ANALYSE_USER_SUMMARY = "https://api.weixin.qq.com/datacube/getusersummary?access_token=ACCESS_TOKEN";
	public static final String WECHAT_ANALYSE_USER_CUMULATE = "https://api.weixin.qq.com/datacube/getusercumulate?access_token=ACCESS_TOKEN";

	/* 生成带参数的二维码 */
	public static final String WECHAT_CREATE_QRCODE_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";

	/* 获取二维码 */
	public static final String WECHAT_GET_QRCODE_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";

	/* 新增永久素材 */
	public static final String WECHAT_ADD_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=TYPE";

	/* 获取永久素材 */
	public static final String WECHAT_GET_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";
	public static final String WECHAT_GET_CALL_BACK_IP = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN";
}
