package cn.com.dingduoduo.interceptor.authority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestAuthenticationSucessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final static Logger logger = LoggerFactory.getLogger(RestAuthenticationSucessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		logger.trace("Succeeded for auth {}", request.getRequestURL());
		clearAuthenticationAttributes(request);
	}
}
