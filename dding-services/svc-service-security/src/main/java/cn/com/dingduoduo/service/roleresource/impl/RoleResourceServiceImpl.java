package cn.com.dingduoduo.service.roleresource.impl;

import cn.com.dingduoduo.dao.roleresource.RoleResourceDAO;
import cn.com.dingduoduo.entity.roleresource.RoleResource;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import cn.com.dingduoduo.service.roleresource.RoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/1/18.
 */
@Service("roleResourceService")
public class RoleResourceServiceImpl extends BaseServiceImpl<RoleResource, RoleResource> implements RoleResourceService {
    @Autowired
    private RoleResourceDAO dao;

    @Autowired
    private void setDao(RoleResourceDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }

}
