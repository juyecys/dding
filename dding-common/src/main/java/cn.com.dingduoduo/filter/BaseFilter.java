package cn.com.dingduoduo.filter;

import cn.com.dingduoduo.utils.common.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 基础filter,扩展了filter的一些功能(如exclude白名单功能)
 * 
 * @author xiaqiang
 *
 */
public abstract class BaseFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(BaseFilter.class);

	private String excludeUrlPatternStr;
	private String includeUrlPatternStr;
	private List<String> excludeUrlPatterns;
	private List<String> includeUrlPatterns;
	/**
	 * Ant pattern地址匹配(规则并不是web.xml的url-pattern,也不是正则)
	 */
	private PathMatcher patternMatcher = new AntPathMatcher();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		excludeUrlPatternStr = filterConfig.getInitParameter("excludeUrlPattern");
		includeUrlPatternStr = filterConfig.getInitParameter("includeUrlPattern");
		logger.debug("Load excludeUrlPattern {} and includeUrlPatternStr {} from config", excludeUrlPatternStr,
				includeUrlPatternStr);
		excludeUrlPatterns = StringUtil.toList(excludeUrlPatternStr);
		includeUrlPatterns = StringUtil.toList(includeUrlPatternStr);
		initInternal(filterConfig);
	}

	@Override
	public void destroy() {

	}

	@Override
	public final void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
			ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		// 不包含部署的应用名
		String uri = request.getServletPath();

		// 必须要过滤的uri,优先级比excludeUrlPatterns高
		if (checkInclude(uri)) {
			doFilterInternal(request, response, chain);
			return;
		}

		// 不需要过滤的uri
		if (checkExclude(uri)) {
			chain.doFilter(req, resp);
			return;
		}

		doFilterInternal(request, response, chain);
		return;
	}

	private final boolean checkExclude(String uri) {
		for (String excludeUrlPattern : excludeUrlPatterns) {
			if (patternMatcher.match(excludeUrlPattern, uri)) {
				logger.info("Uri {} matches excludeUrlPattern {} , will not filter", uri, excludeUrlPattern);
				return true;
			}
		}
		return false;
	}

	private final boolean checkInclude(String uri) {
		for (String includeUrlPattern : includeUrlPatterns) {
			if (patternMatcher.match(includeUrlPattern, uri)) {
				logger.info("Uri {} matches includeUrlPattern {} , must be filter", uri, includeUrlPattern);
				return true;
			}
		}
		return false;
	}

	/**
	 * 子类重写,增加filter doFilter逻辑
	 * 
	 * @param req
	 * @param resp
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doFilterInternal(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
			ServletException {
		chain.doFilter(req, resp);
	}

	/**
	 * 子类重写,增加init逻辑
	 * 
	 * @param filterConfig
	 */
	public void initInternal(FilterConfig filterConfig) {

	}
}
