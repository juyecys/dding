package cn.com.dingduoduo.service.menu.impl;

import cn.com.dingduoduo.dao.menu.MenuDAO;
import cn.com.dingduoduo.entity.menu.Menu;
import cn.com.dingduoduo.entity.resource.Resource;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import cn.com.dingduoduo.service.menu.MenuService;
import cn.com.dingduoduo.service.resource.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jeysine on 2018/3/5.
 */
@Service("menuService")
public class MenuServiceImpl extends BaseServiceImpl<Menu, Menu> implements MenuService {
    @Autowired
    private MenuDAO dao;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    public void setMenuDAO(MenuDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }

    @Override
    public Menu createOrUpdate(Menu menu) throws Exception {
        if (menu.getId() == null) {
            Resource resource = new Resource();
            resource.setResource(menu.getResource());
            resource.setType(Resource.TypeEnum.MENU.name());
            resource = resourceService.createOrUpdate(resource);
            menu.setResourceId(resource.getId());
            menu = create(menu);
            return findOneByCondition(menu);
        } else {
            Resource resource = new Resource();
            resource.setId(menu.getResourceId());
            resource.setResource(menu.getResource());
            resource = resourceService.createOrUpdate(resource);
            resourceService.createOrUpdate(resource);
            menu = update(menu);
            return findOneByCondition(menu);
        }
    }
}
