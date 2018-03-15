package cn.com.dingduoduo.dao.wechat.localwechatmenu;

import cn.com.dingduoduo.dao.common.BaseDAO;
import cn.com.dingduoduo.entity.wechat.localwechatmenu.LocalWechatMenu;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface LocalWechatMenuDAO extends BaseDAO<LocalWechatMenu, LocalWechatMenu> {
    List<LocalWechatMenu> findByCondition(LocalWechatMenu localWechatMenu);
}
