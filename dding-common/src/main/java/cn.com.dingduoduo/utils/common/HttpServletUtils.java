package cn.com.dingduoduo.utils.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Created by Administrator on 2015/12/28.
 */
public class HttpServletUtils {

	public final static String REQ_SOURCE_WECHAT = "cn.com.dingduoduo.api.admin.wechat";
	public final static String REQ_SOURCE_APP = "app";
	public final static String REQ_SOURCE_WEB = "web";
	public final static String REQ_SOURCE_UNKNOW = "unknow";

	/**
	 * 获取客户端地址，优先拿x-real-ip，然后是xff，然后是socket的地址 在SLB部署的情况下，nginx proxy的设置不能使用
	 * proxy_set_header X-Real-IP $remote_addr; 因为nginx的remote_addr是SLB的？
	 * 
	 * 无SLB的情况，不能使用XFF，因为有可能有客户端故意使用XFF来设置假地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getRealIp(HttpServletRequest request) {
		String realIp = request.getHeader("X-Real-IP");
		String xff = request.getHeader("X-Forwarded-For");
		// nginx 前边如果有SLB就不设置realIP，有realIP说明没有用SLB
		if (!StringUtils.isEmpty(realIp)) {
			return realIp;
		}
		if (!StringUtils.isEmpty(xff)) {
			// XFF header format X-Forwarded-For: client, SLB
			String[] forwardIps = xff.split(",");
			if (forwardIps.length > 0) {
				return forwardIps[0];
			} else {
				return request.getRemoteAddr();
			}
		} else {
			return request.getRemoteAddr();
		}
	}

	/**
	 * 获得请求的设备来源
	 * 
	 * @param request
	 * @return
	 */
	public static String getDeviceType(HttpServletRequest request) {
		String agent = request.getHeader("user-agent");

		if (StringUtil.isNotEmpty(agent)) {
			agent = agent.toLowerCase().trim();
		} else {
			return REQ_SOURCE_UNKNOW;
		}

		if (agent.indexOf("micromessenger") != -1) {
			return REQ_SOURCE_WECHAT;
		}

		String[] mobileAgents = { "iphone", "android", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
				"opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
				"nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
				"docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
				"techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
				"wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
				"pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
				"240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
				"blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
				"kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
				"mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
				"prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
				"smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",
				"voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
				"Googlebot-Mobile" };

		if (ArrayUtils.contains(mobileAgents, agent)) {
			return REQ_SOURCE_APP;
		}

		return REQ_SOURCE_WEB;
	}

	/**
	 * 获取Host，优先拿X-Forwarded-Proto，Host
	 *
	 * @param request
	 * @return
	 */
	public static String getRealHost(HttpServletRequest request) {
		String realProto = request.getHeader("X-Forwarded-Proto");
		return realProto + "://" + request.getServerName();
	}

	/**
	 * 获取X-Cys-Client,判断api调用客户端平台
	 *
	 * @param request
	 * @return
	 */
	public static String getClient(HttpServletRequest request) {
		return request.getHeader("X-Cys-Client");
	}

	/**
	 * 获取Authorization中的CYSTOKEN值
	 *
	 * @param request
	 * @return
	 */
	public static String getCysToken(HttpServletRequest request) {
		String headerValue = request.getHeader("Authorization");
		if (headerValue == null || !headerValue.startsWith("CYSTOKEN")) {
			return null;
		}
		String splittedHeader[] = headerValue.split(" ");
		if (splittedHeader.length != 2) {
			return null;
		}
		if (splittedHeader[1].trim().isEmpty()) {
			return null;
		}
		return splittedHeader[1];
	}
}
