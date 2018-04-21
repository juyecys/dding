package cn.com.dingduoduo.api.wp.login;

import cn.com.dingduoduo.contants.wp.WechatPublicContants;
import cn.com.dingduoduo.entity.wechat.auth.WechatAuthAccessToken;
import cn.com.dingduoduo.entity.wechat.user.WechatUser;
import cn.com.dingduoduo.entity.wechatuser.LocalWechatUser;
import cn.com.dingduoduo.entity.wechatuser.LocalWechatUserDTO;
import cn.com.dingduoduo.service.wechat.auth.WechatAuthService;
import cn.com.dingduoduo.service.wechat.user.WechatUserService;
import cn.com.dingduoduo.service.wechatuser.LocalWechatUserService;
import cn.com.dingduoduo.utils.common.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * Created by jeysine on 2018/2/8.
 */
@RestController
@RequestMapping(value = { "/ding/wp/public/login" }, produces = "application/json")
public class PublicWPLoginController {
    private static final Logger logger = LoggerFactory.getLogger(PublicWPLoginController.class);

    @Autowired
    private LocalWechatUserService localWechatUserService;

    @Autowired
    private WechatAuthService wechatAuthServiceRPC;

    @Autowired
    private WechatUserService wechatUserService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void login(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        StringBuilder nextDingUrl = new StringBuilder(request.getParameter("ding_url"));
        String source = request.getParameter("source");
        logger.debug("source: {}", source);
        // 其它的参数应该作为目标url的参数

        // 避免vue框架处理url, 把#/加到url末尾
        nextDingUrl.append("#/");

        Enumeration<String> parameters = request.getParameterNames();
        String paramJoinTag = "?";
        while (parameters.hasMoreElements()) {
            String paramName = (String) parameters.nextElement();
            if (filterRequestParams(paramName)) {
                if (!"&".equals(paramJoinTag) && nextDingUrl.toString().indexOf('?', 1) > -1) {
                    paramJoinTag = "&";
                }

                String[] values = request.getParameterValues(paramName);
                nextDingUrl.append(paramJoinTag).append(paramName).append("=").append(values[0]);
                logger.debug("nextDingUrl: {}", nextDingUrl.toString());
            }
        }

        logger.debug("AuthWechat get wechat ding_url {}", nextDingUrl.toString());
        WechatAuthAccessToken wechatAuthAccessToken = null;
        try {
            String openid = (String) request.getSession().getAttribute(WechatPublicContants.SESSION_OPENID);
            if (openid == null) {
                wechatAuthAccessToken = wechatAuthServiceRPC.getAuthAccessTokenByCode(code);
                if (wechatAuthAccessToken.getOpenId() != null) {
                    LocalWechatUserDTO user = createOrUpdateUser(wechatAuthAccessToken, source);
                    request.getSession().setAttribute(WechatPublicContants.SESSION_OPENID, user.getOpenId());
                    request.getSession().setAttribute(WechatPublicContants.SESSION_NICKNAME, user.getNickName());

                    request.getSession().setAttribute(WechatPublicContants.SESSION_USERID, user.getId());
                    request.getSession().setAttribute(WechatPublicContants.SESSION_UNIONID, user.getUnionId());
                } else {
                    logger.error("failed to get wechat auth accessToken");
                }
            }
            response.sendRedirect(nextDingUrl.toString());
        } catch (Exception e) {
            logger.debug("error: {}", e);
        }
    }

    private boolean filterRequestParams(String paramName) {
        return !paramName.equals("code") && !paramName.equals("ding_url") && !paramName.equals("state")
                && !paramName.equals("connect_redirect") && !paramName.equals("scope") && !paramName.equals("response_type");
    }

    private LocalWechatUserDTO createOrUpdateUser(WechatAuthAccessToken wechatAuthAccessToken, String source) throws Exception {
        LocalWechatUserDTO user = new LocalWechatUserDTO();
        user.setOpenId(wechatAuthAccessToken.getOpenId());
        user = localWechatUserService.findOneByCondition(user);
        if (user != null) {
            logger.debug("user login success: {}", user.toString());
            if (StringUtil.isEmpty(source)) {
                return user;
            }
            user.setSource(source);
        } else {
            WechatUser wechatUser = wechatUserService.getWechatUserInfoByAuth(wechatAuthAccessToken.getAccessToken(), wechatAuthAccessToken.getOpenId());
            logger.debug("user is not exist, add to database: {}", wechatUser);
            user = new LocalWechatUserDTO();
            user.setOpenId(wechatUser.getOpenId());
            user.setCity(wechatUser.getCity());
            user.setGender(wechatUser.getSex());
            user.setNickName(wechatUser.getNickname());
            user.setProvince(wechatUser.getProvince());
            user.setUnionId(wechatUser.getUnionId());
            user.setHeadImgUrl(wechatUser.getHeadImgUrl());
            user.setCountry(wechatUser.getCountry());
            user.setSubscribe(0);

            if (StringUtil.isEmpty(source)) {
                source = LocalWechatUser.SourceEnum.YI_KANG_BAO.name();
            }
            user.setSource(source);
        }
        logger.debug("add to database: {}", user);
        localWechatUserService.createOrUpdate(user);
        return user;
    }

    @RequestMapping(value = "/delSession", method = RequestMethod.GET)
    public void deleteSession(HttpServletRequest request, HttpServletResponse response) {
        for (Enumeration items = request.getSession().getAttributeNames(); items.hasMoreElements(); ) {
            String item = (String) items.nextElement();
            request.getSession().removeAttribute(item);
            logger.debug("remove session success: {}", item);
        }
    }
}
