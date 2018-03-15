package cn.com.dingduoduo.service.user.impl;


import cn.com.dingduoduo.dao.user.UserDAO;
import cn.com.dingduoduo.entity.user.User;
import cn.com.dingduoduo.entity.user.UserDTO;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import cn.com.dingduoduo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/17.
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User, UserDTO> implements UserService {
    @Autowired
    private UserDAO dao;

    @Autowired
    public void setDao(UserDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = new UserDTO();
        user.setName(username);
        user = dao.findOneByCondition(user);

       /* UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setUserName(username);
        List<UserRoleDTO> userRoleList = userRoleDAO.findByCondition(userRoleDTO);*/
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();


        list.add(new SimpleGrantedAuthority(user.getRoleCode()));

        user.setGrantedAuthorities(list);
        return user;
    }

}
