package cn.com.dingduoduo.interceptor.authority;

/**
 * Created by Kevin on 15/4/24.
 */

import cn.com.dingduoduo.dao.resource.ResourceDAO;
import cn.com.dingduoduo.dao.role.RoleDAO;
import cn.com.dingduoduo.entity.resource.Resource;
import cn.com.dingduoduo.entity.role.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/*
 *
 * 最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。
 * 注意，我例子中使用的是AntUrlPathMatcher这个path matcher来检查URL是否与资源定义匹配，
 * 事实上你还要用正则的方式来匹配，或者自己实现一个matcher。
 *
 * 此类在初始化时，应该取到所有资源及其对应角色的定义
 *
 * 说明：对于方法的spring注入，只能在方法和成员变量里注入，
 * 如果一个类要进行实例化的时候，不能注入对象和操作对象，
 * 所以在构造函数里不能进行操作注入的数据。
 */
@Service
public class MyInvocationSecurityMetadataSourceService implements
        FilterInvocationSecurityMetadataSource {

    private ResourceDAO resourceDAO;
    private RoleDAO roleDAO;

    private Logger logger = LoggerFactory.getLogger(MyInvocationSecurityMetadataSourceService.class);

//    private RequestMatcher urlMatcher = new AntPathRequestMatcher();
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
    @Autowired
    public MyInvocationSecurityMetadataSourceService(ResourceDAO resourceDAO,RoleDAO roleDAO) {
        this.resourceDAO = resourceDAO;
        this.roleDAO = roleDAO;
        loadResourceDefine();
    }

    private void loadResourceDefine() {
        List<Role> roles = roleDAO.findByCondition(new Role());
        resourceMap = new HashMap<>();
        
        for (Role role : roles) {
            ConfigAttribute ca = new SecurityConfig(role.getCode());// "ROLE_ADMIN"
            // atts.add(ca);

            List<Resource> resources = resourceDAO.findResourceByRoleName(role.getName());
            for (Resource res : resources) {
                String url = res.getResource();
                // 判断资源文件和权限的对应关系，如果已经存在，要进行增加
                if(url == null || url.equals(""))
                {
                    continue;
                }
                if (resourceMap.containsKey(url)) {
                    Collection<ConfigAttribute> value = resourceMap.get(url);
                    value.add(ca);
                    resourceMap.put(url, value);
                    // "log.jsp","role_user,role_admin"
                } else {
                    Collection<ConfigAttribute> atts = new ArrayList<>();
                    atts.add(ca);
                    resourceMap.put(url, atts);
                }
            }
        }
    }

    // According to a URL, Find out permission configuration of this URL.
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) object;
        HttpServletRequest request = filterInvocation.getHttpRequest();
        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String resURL = ite.next();
            logger.debug("res url: {}", resURL);

            RequestMatcher requestMatcher = new AntPathRequestMatcher(resURL);

            if (requestMatcher.matches(request)) {
                logger.debug("抓取到url");
                return resourceMap.get(resURL);
            }
        }
        return null;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

}

