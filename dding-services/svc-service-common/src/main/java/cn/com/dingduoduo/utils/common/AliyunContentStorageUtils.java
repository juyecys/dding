package cn.com.dingduoduo.utils.common;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Random;

/**
 * @author cysfrank
 * 
 *         云存储相关工具
 *
 */
public class AliyunContentStorageUtils {
	private final static Logger logger = LoggerFactory.getLogger(AliyunContentStorageUtils.class);

	/**
	 * 存储服务器对象的访问url前缀， 如http://cys-static-test.oss-cn-shenzhen.aliyuncs.com/
	 */
	public static String OBJ_ACCESS_URL_PREFIX;

	public static String OBJ_ACCESS_SECURE_URL_PREFIX;
	
	public static String OBJ_INTERNAL_ACCESS_SECURE_URL_PREFIX;

	/* 图片缩略图访问地址前缀 */
	public static String THUMBNAIL_IMG_URL_PREFIX;

	/* 图片缩略图宽度 */
	public static int THUMBNAIL_IMG_WIDTH;

	/**
	 * 从配置文件中得到存储对象的url前缀
	 * 
	 * @param urlPrefix
	 */
	public void setOBJ_ACCESS_URL_PREFIX(String urlPrefix) {
		OBJ_ACCESS_URL_PREFIX = urlPrefix;
		logger.trace("synthesized oss url prefix: {}", OBJ_ACCESS_URL_PREFIX);
	}

	public void setOBJ_ACCESS_SECURE_URL_PREFIX(String urlPrefix) {
		OBJ_ACCESS_SECURE_URL_PREFIX = urlPrefix;
		logger.trace("synthesized oss secure url prefix: {}", OBJ_ACCESS_SECURE_URL_PREFIX);
	}
	
	public void setOBJ_INTERNAL_ACCESS_SECURE_URL_PREFIX(String urlPrefix) {
		OBJ_INTERNAL_ACCESS_SECURE_URL_PREFIX = urlPrefix;
		logger.trace("synthesized oss internal secure url prefix: {}", OBJ_INTERNAL_ACCESS_SECURE_URL_PREFIX);
	}

	/**
	 * 从配置文件中得到存储对象的url前缀
	 * 
	 */
	public void setTHUMBNAIL_IMG_WIDTH(int width) {
		THUMBNAIL_IMG_WIDTH = width;
		logger.trace("thumbnail image width: {}", THUMBNAIL_IMG_WIDTH);
	}

	/**
	 * 从配置文件中得到存储对象的thumbnail url前缀
	 * 
	 * @param urlPrefix
	 */
	public void setTHUMBNAIL_IMG_URL_PREFIX(String urlPrefix) {
		THUMBNAIL_IMG_URL_PREFIX = urlPrefix;
		logger.trace("synthesized oss thumbnail url prefix: {}", THUMBNAIL_IMG_URL_PREFIX);
	}

	/**
	 * 根据一个或多个key得到其访问url 注意：多个key必须以;或,分隔. 返回值为;分隔 例： key -> http://xxx.yyy/key
	 * key1;key2 -> http://xxx.yyy/key1;http://xxx.yyy/key2
	 * 
	 * @return
	 */
	public static String getFullAccessUrlForKey(String keys) {
		return wrapKeys(keys, OBJ_ACCESS_URL_PREFIX, "");
	}

	/**
	 * 返回HTTPS前缀的链接
	 * 
	 * @param keys
	 * @return
	 */
	public static String getSecuredFullAccessUrlForKey(String keys) {
		return wrapKeys(keys, OBJ_ACCESS_SECURE_URL_PREFIX, "");
	}
	
	/**
	 * 返回HTTPS前缀的OSS内网访问链接
	 * 
	 * @param keys
	 * @return
	 */
	public static String getSecuredFullInternalAccessUrlForKey(String keys) {
		return wrapKeys(keys, OBJ_INTERNAL_ACCESS_SECURE_URL_PREFIX, "");
	}

	/**
	 * 对于图片，根据一个或多个key得到其缩略图访问url 注意：多个key必须以;分隔. 返回值亦为;分隔 例： key ->
	 * http://xxx.yyy/key key1;key2 ->
	 * http://xxx.yyy/key1@256w;http://xxx.yyy/key2@256w
	 * 
	 * @return
	 */
	public static String getImageThumbnailUrlForKey(String keys) {
		return wrapKeys(keys, THUMBNAIL_IMG_URL_PREFIX, "@" + THUMBNAIL_IMG_WIDTH + "w");
	}

	private static String wrapKeys(String keys, String prefix, String postfix) {
		if (StringUtils.isEmpty(keys)) {
			logger.warn("key should not be empty!");
			Exception e = new Exception();
			String trace = "";
			// 只打印调用栈的前10个调用应该就可以知道context了
			for (int i = 0; i < 10; i++) {
				trace += e.getStackTrace()[i].toString();
			}
			logger.warn("call trace:{}", trace);

			return null;
		}
		
		String delimiter = ";";
		
		if (keys.contains(",")) {
			delimiter = ",";
		}
		
		String keyList[] = keys.split(delimiter);

		StringBuilder fullUrlStr = new StringBuilder();

		for (String key : keyList) {
			fullUrlStr.append(prefix).append(key.trim()).append(postfix).append(";");
		}

		return fullUrlStr.substring(0, fullUrlStr.length() - 1);
	}

	/**
	 * 从一个或多个url中取出key 注意：多个url必须以;分隔. 返回值亦为;分隔 例： http://xxx.yyy/url -> url
	 * http://xxx.yyy/key1;http://xxx.yyy/key2 -> key1;key2
	 * 
	 * @param urlStr
	 * @return
	 */
	public static String getKeyFromFullAccessUrl(String urlStr) {
		if (urlStr == null || !urlStr.startsWith(OBJ_ACCESS_URL_PREFIX)) {
			logger.error("invalid full access url: {}", urlStr);
			return urlStr;
		}
		return urlStr.replaceAll(OBJ_ACCESS_URL_PREFIX, "");
	}

	/**
	 * 
	 * 根据path和文件扩展名生成云存储的key
	 * 
	 * @param path
	 *            should never start with '/'
	 * @param extension
	 *            e.g html, jpg, txt
	 * @return
	 */
	public static String generateKey(String path, String extension) {
		Random random = new Random();
		if (path.startsWith("/")) {
			logger.error("path should NEVER start with '/' ");
			return null;
		}
		if (!path.endsWith("/")) {
			path = path + "/";
		}
		int r;
		do {
			r = random.nextInt();
		} while (r == Integer.MIN_VALUE);
		String key = path + DateUtils.format(new Date(), "yyyyMMddHHmmss") + "_" + Math.abs(r) + "." + extension;

		return key;
	}

	public static void main(String[] args) {
		OBJ_ACCESS_URL_PREFIX = "http://cys-static-test.oss-cn-shenzhen.aliyuncs.com/";
		String keys = "order/service_order/20161103144126920.jpg,order/service_order/20161103144127947.jpg,order/service_order/20161103144128172.jpg";
		String fullUrsStr = getFullAccessUrlForKey(keys);
		System.out.println(fullUrsStr);
		System.out.println(getKeyFromFullAccessUrl(fullUrsStr));
		THUMBNAIL_IMG_URL_PREFIX = "http://cys-static-test.img-cn-shenzhen.aliyuncs.com/";
		THUMBNAIL_IMG_WIDTH = 256;
		String thumbnailUrls = getImageThumbnailUrlForKey(keys);
		System.out.println(thumbnailUrls);

		System.out.println(generateKey("this/is/a/path/", "html"));

	}
}
