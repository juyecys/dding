package cn.com.dingduoduo.dao.user;


import cn.com.dingduoduo.dao.common.BaseDAO;
import cn.com.dingduoduo.entity.user.User;
import cn.com.dingduoduo.entity.user.UserDTO;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/1/17.
 */
@Component
public interface UserDAO extends BaseDAO<User, UserDTO> {
}
