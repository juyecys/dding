package cn.com.dingduoduo.service.user;


import cn.com.dingduoduo.entity.user.User;
import cn.com.dingduoduo.entity.user.UserDTO;
import cn.com.dingduoduo.service.common.BaseService;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by Administrator on 2018/1/17.
 */
public interface UserService extends  BaseService<User, UserDTO>, UserDetailsService {
}
