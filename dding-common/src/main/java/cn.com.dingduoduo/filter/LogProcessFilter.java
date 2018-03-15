package cn.com.dingduoduo.filter;

import cn.com.dingduoduo.contants.SystemConstants;
import cn.com.dingduoduo.utils.common.CommonUtils;
import cn.com.dingduoduo.utils.common.HttpServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 给所有http api请求,log框架的前置处理
 * 
 * @author xiaqiang
 *
 */
@Component("logProcessFilter")
public class LogProcessFilter extends BaseFilter {
	private static Logger logger = LoggerFactory.getLogger(LogProcessFilter.class);

	@Override
	public void doFilterInternal(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
			ServletException {
		// 在请求之前设置log的traceid参数,方便微服务间调用跟踪
		String traceid = CommonUtils.genTraceId();
		MDC.put(SystemConstants.TRACE_ID, traceid);
		logger.info("set traceid {} in LogProcessFilter {}", traceid, dumpRequestInfo((HttpServletRequest) req));
		chain.doFilter(req, resp);
	}

	/**
	 * Dump comma separated HTTP request Info
	 * 
	 * @param request
	 * @return
	 */
	private String dumpRequestInfo(HttpServletRequest request) {
		// Maybe proxied by nginx
		String xff = request.getHeader("X-Forwarded-For");
		if (xff == null) {
			xff = HttpServletUtils.getRealIp(request);
			// xff = request.getRemoteAddr();
		}
		StringBuffer sb = new StringBuffer();
		sb.append(request.getRequestURL()).append(",").append(request.getQueryString()).append(",")
				.append(request.getHeader("Authorization")).append(",").append(request.getMethod()).append(",")
				.append(xff);
		return sb.toString();
	}
}
