package cn.com.dingduoduo.service.resource.impl;

import cn.com.dingduoduo.dao.resource.ResourceDAO;
import cn.com.dingduoduo.entity.resource.Resource;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import cn.com.dingduoduo.service.resource.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("resourceService")
public class ResourceServiceImpl extends BaseServiceImpl<Resource, Resource> implements ResourceService {

    @Autowired
    private ResourceDAO dao;

    @Autowired
    private void setDao(ResourceDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }

    @Override
    public List<Resource> findResourceByRoleName(String name) {
        return dao.findResourceByRoleName(name);
    }

}
