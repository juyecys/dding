package cn.com.dingduoduo.service.authority.impl;

import cn.com.dingduoduo.dao.authority.AuthorityDAO;
import cn.com.dingduoduo.entity.authority.Authority;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jeysine on 2018/1/23.
 */
@Service("authorityService")
public class AuthorityServiceImpl extends BaseServiceImpl<Authority, Authority>{
    @Autowired
    private AuthorityDAO dao;

    @Autowired
    private void setDao(AuthorityDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }
}
