package cn.com.dingduoduo.service.wechatuser;



import cn.com.dingduoduo.entity.wechatuser.LocalWechatUserDTO;
import cn.com.dingduoduo.service.common.BaseService;
import cn.com.dingduoduo.entity.wechatuser.LocalWechatUser;

import java.util.List;

/**
 * Created by jeysine on 2018/1/24.
 */
public interface LocalWechatUserService extends BaseService<LocalWechatUser, LocalWechatUserDTO> {
    List<LocalWechatUserDTO> findByUnsynchronous();
    void synchronousUser(LocalWechatUser wechatUser);

    Integer countUsers(LocalWechatUserDTO wechatUser);
}
