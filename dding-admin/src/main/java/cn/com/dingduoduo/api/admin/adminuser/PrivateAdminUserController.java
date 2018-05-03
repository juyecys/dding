package cn.com.dingduoduo.api.admin.adminuser;

import cn.com.dingduoduo.api.common.ApiResult;

import cn.com.dingduoduo.utils.common.MD5Util;
import cn.com.dingduoduo.entity.common.Page;
import cn.com.dingduoduo.entity.user.User;
import cn.com.dingduoduo.entity.user.UserDTO;
import cn.com.dingduoduo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jeysine on 2018/3/5.
 */
@RestController
@RequestMapping(value = {"/ding/mg/private/user", "/ding/mg/public/user"}, produces = "application/json")
public class PrivateAdminUserController {
    @Autowired
    private UserService userService;

    //这是测试提交

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> addUser(@RequestBody User user) throws Exception {
        //user = userService.createOrUpdate(user);
        user.setPassword(MD5Util.MD5Encode(user.getPassword(), "UTF-8").toLowerCase());
        user = userService.createOrUpdate(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO = userService.findOneByCondition(userDTO);
        return new ResponseEntity<>(ApiResult.success(userDTO), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> deleteUser(@RequestParam("id") String id) throws Exception {
        userService.deleteById(id);
        return new ResponseEntity<>(ApiResult.success(), HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> findPage(UserDTO user) throws Exception {
        Page<UserDTO> page = userService.findByConditionPage(user);
        return new ResponseEntity<>(ApiResult.success(page), HttpStatus.OK);
    }
}