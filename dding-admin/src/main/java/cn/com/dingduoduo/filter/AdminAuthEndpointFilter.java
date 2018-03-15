package cn.com.dingduoduo.filter;

import cn.com.dingduoduo.config.common.CommonContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jeysine on 2018/2/20.
 */
@Component("adminAuthEndpointFilter")
public class AdminAuthEndpointFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(AdminAuthEndpointFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = null;
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal != null && principal instanceof UserDetails) {
                userName = ((UserDetails) principal).getUsername();
            } else if (principal != null) {
                userName = principal.toString();
            }
        }
        CommonContextHolder.setUserName(userName);
        chain.doFilter(request, response);
        CommonContextHolder.clear();
    }
}
