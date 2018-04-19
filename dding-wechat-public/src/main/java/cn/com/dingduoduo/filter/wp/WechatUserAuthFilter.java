package cn.com.dingduoduo.filter.wp;

import cn.com.dingduoduo.config.common.WechatContextHolder;
import cn.com.dingduoduo.contants.wp.WechatPublicContants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by jeysine on 2018/2/7.
 * 微信用户身份验证
 */
@Component("wechatUserAuthFilter")
public class WechatUserAuthFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(WechatUserAuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (req.getSession().getAttribute(WechatPublicContants.SESSION_OPENID) == null) {
            PrintWriter out = resp.getWriter();
            out.write("{\"code\":\"4001\",\"desc\":\"权限不足，请联系管理员!\"}");
            out.flush();
            out.close();
            return;
        }
        WechatContextHolder.setOpenId((String) req.getSession().getAttribute(WechatPublicContants.SESSION_OPENID));
        WechatContextHolder.setNickName((String) req.getSession().getAttribute(WechatPublicContants.SESSION_NICKNAME));
        WechatContextHolder.setUserName((String) req.getSession().getAttribute(WechatPublicContants.SESSION_NICKNAME));
        WechatContextHolder.setUserId((String) req.getSession().getAttribute(WechatPublicContants.SESSION_USERID));
        WechatContextHolder.setUnionId((String) req.getSession().getAttribute(WechatPublicContants.SESSION_UNIONID));
        logger.debug("verify wechat user auth success,openid:{},nickname:{},userId:{},unionId:{} ",WechatContextHolder.getOpenId(),
                WechatContextHolder.getNickName(), WechatContextHolder.getUserId(), WechatContextHolder.getUnionId());
        chain.doFilter(request, response);
        WechatContextHolder.clear();
    }

    @Override
    public void destroy() {

    }
}
