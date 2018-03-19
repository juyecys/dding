package cn.com.dingduoduo.service.wechat.localMenu.impl;


import cn.com.dingduoduo.utils.common.StringUtil;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.dingduoduo.dao.wechat.localwechatmenu.LocalWechatMenuDAO;
import cn.com.dingduoduo.entity.wechat.localwechatmenu.LocalWechatMenu;
import cn.com.dingduoduo.service.wechat.localMenu.LocalWechatMenuService;

import java.util.List;

@Service("localWechatMenuService")
public class LocalWechatMenuServiceImpl extends BaseServiceImpl<LocalWechatMenu, LocalWechatMenu> implements LocalWechatMenuService {
    @Autowired
    private LocalWechatMenuDAO dao;

    @Autowired
    private void setDao(LocalWechatMenuDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }

    @Override
    public List<LocalWechatMenu> findByCondition(LocalWechatMenu localWechatMenu) {
        return dao.findByCondition(localWechatMenu);
    }

    @Override
    public LocalWechatMenu createOrUpdate(LocalWechatMenu menu) throws Exception {
        LocalWechatMenu check = new LocalWechatMenu();
        check.setKey(menu.getKey());
        if (StringUtil.isEmpty(menu.getId())) {
            if (isExistKey(menu)) {
                throw new Exception("微信菜单key不能重复");
            } else {
                menu = create(menu);
            }
        } else {
            menu = update(menu);
        }
        return menu;
    }

    private Boolean isExistKey(LocalWechatMenu menu) {
        if (menu.getKey() == null) {
            return false;
        }
        LocalWechatMenu search = new LocalWechatMenu();
        search.setKey(menu.getKey());
        search = findOneByCondition(search);
        if (search == null) {
            return true;
        }
        return false;
    }
}
