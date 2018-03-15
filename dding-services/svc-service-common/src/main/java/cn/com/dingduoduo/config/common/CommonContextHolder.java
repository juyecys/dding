package cn.com.dingduoduo.config.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 线程数据存取类（包含登录信息等）
 * 
 * @author jeysine
 *
 */
public class CommonContextHolder {
	private static ThreadLocal<HashMap<String, Object>> holder = new ThreadLocal<HashMap<String, Object>>() {
		@Override
		protected HashMap<String, Object> initialValue() {
			return new HashMap<>();
		}
	};

	private static final String KEY_USER_ID = "user_id";
	private static final String KEY_USER_NAME = "user_name";
	public CommonContextHolder() {
	}
	/**
	 * 清除ThreadLocal中所有数据
	 */
	public static void clear() {
		holder.remove();
	}

	/**
	 * Map经历了初始化,一定不为null
	 * 
	 * @return
	 */
	public static Map<String, Object> getHolder() {
		return holder.get();
	}

	/**
	 * 存放数据
	 * 
	 * @param key
	 * @param data
	 */
	public static void setData(String key, Object data) {
		getHolder().put(key, data);
	}

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */
	public static Object getData(String key) {
		return getHolder().get(key);
	}


	public static void setUserId(String userId) {
		setData(KEY_USER_ID, userId);
	}

	public static Object getUserId(){
		return getData(KEY_USER_ID);
	}

	public static void setUserName(String userName) {
		setData(KEY_USER_NAME, userName);
	}

	public static String getUserName() {
		return (String) getData(KEY_USER_NAME);
	}

}
