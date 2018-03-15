package cn.com.dingduoduo.api.admin.adminuser;

import cn.com.dingduoduo.api.common.ApiCodes;
import cn.com.dingduoduo.api.common.ApiResult;
import cn.com.dingduoduo.utils.common.MD5Util;
import cn.com.dingduoduo.entity.user.User;
import cn.com.dingduoduo.entity.user.UserDTO;
import cn.com.dingduoduo.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2018/1/16.
 */

@RestController
@RequestMapping(value = { "/ding/mg/public/user" }, produces = "application/json")
public class PublicAdminUserController {
    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(PublicAdminUserController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> login(@RequestBody User user, HttpServletRequest request) {
        String password = user.getPassword();
        if (password == null || user.getName() == null) {
            return new ResponseEntity<>(ApiResult.error(ApiCodes.STATUS_INVALID_PARAMETER, "用户名或者密码不能为空"), HttpStatus.OK);
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEnabled(true);
        user = userService.findOneByCondition(userDTO);
        if (user == null) {
            return new ResponseEntity<>(ApiResult.error(ApiCodes.STATUS_NOT_FOUND, "不存在该用户"), HttpStatus.OK);
        }
        String md5Password = MD5Util.MD5Encode(password, "UTF-8").toLowerCase();
        if (md5Password.equals(user.getPassword())) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getName(), password);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession session = request.getSession();
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
//            CommonContextHolder.setUserId(user.getId());
            return new ResponseEntity<>(ApiResult.success(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ApiResult.error(ApiCodes.STATUS_WRONG_OLD_PASSWORD, "密码错误"), HttpStatus.OK);
    }


    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public void test(@RequestBody List<String> openid) {

    }
}
