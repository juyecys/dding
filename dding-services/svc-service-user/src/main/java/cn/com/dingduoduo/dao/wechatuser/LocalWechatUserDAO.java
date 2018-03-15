package cn.com.dingduoduo.dao.wechatuser;


import cn.com.dingduoduo.dao.common.BaseDAO;
import cn.com.dingduoduo.entity.wechatuser.LocalWechatUser;
import cn.com.dingduoduo.entity.wechatuser.LocalWechatUserDTO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jeysine on 2018/1/24.
 */
@Component
public interface LocalWechatUserDAO extends BaseDAO<LocalWechatUser, LocalWechatUserDTO> {
    List<LocalWechatUserDTO> findByUnsynchronous();
    void synchronousUser(LocalWechatUser wechatUser);
    Integer countUsers(LocalWechatUser wechatUser);
}
