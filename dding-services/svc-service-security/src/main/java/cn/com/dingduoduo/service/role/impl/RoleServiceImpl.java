package cn.com.dingduoduo.service.role.impl;

import cn.com.dingduoduo.dao.role.RoleDAO;
import cn.com.dingduoduo.entity.role.Role;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import cn.com.dingduoduo.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role, Role> implements RoleService {
    @Autowired
    private RoleDAO dao;

    @Autowired
    private void setDao(RoleDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }
}
