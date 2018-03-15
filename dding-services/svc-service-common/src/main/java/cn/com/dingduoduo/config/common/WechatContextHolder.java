package cn.com.dingduoduo.config.common;

/**
 * 线程数据存取类（包含登录信息等）
 * 
 * @author jeysine
 *
 */
public class WechatContextHolder extends CommonContextHolder{

	private static final String KEY_MINI_PROGRAM_ACCOUNT = "mini_program_account";
	private static final String KEY_OPEN_ID = "open_id";
	private static final String KEY_UNION_ID = "union_id";
	private static final String KEY_NICK_NAME = "nick_name";

	private WechatContextHolder() {
		super();
	}


	public static void setOpenId(String openId) {
		setData(KEY_OPEN_ID, openId);
	}
	
	public static void setUnionId(String unionId) {
		setData(KEY_UNION_ID, unionId);
	}

	public static String getOpenId() {
		return (String) getData(KEY_OPEN_ID);
	}
	public static String getUnionId() {
		return (String) getData(KEY_UNION_ID);
	}

	public static void setNickName(String nickName) {
		setData(KEY_NICK_NAME, nickName);
	}

	public static String getNickName() {
		return (String) getData(KEY_NICK_NAME);
	}
}
