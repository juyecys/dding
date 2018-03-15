package cn.com.dingduoduo.api.admin.role;


import cn.com.dingduoduo.api.common.ApiResult;
import cn.com.dingduoduo.entity.common.Page;
import cn.com.dingduoduo.entity.role.Role;
import cn.com.dingduoduo.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by jeysine on 2018/1/22.
 */
@RestController
@RequestMapping(value = "/ding/mg/private/role", produces = "application/json")
public class PrivateAdminRoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> createOrUpdateRole(@RequestBody Role role) throws Exception {
        role = roleService.createOrUpdate(role);
        return new ResponseEntity<>(ApiResult.success(role), HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getPage(Role role) {
        Page<Role> page = roleService.findByConditionPage(role);
        return new ResponseEntity<>(ApiResult.success(page), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> createOrUpdateRole(@RequestParam("id") String id) throws Exception {
        roleService.deleteById(id);
        return new ResponseEntity<>(ApiResult.success(), HttpStatus.OK);
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getList(Role role) {
        List<Role> list = roleService.findByCondition(role);
        return new ResponseEntity<>(ApiResult.success(list), HttpStatus.OK);
    }
}
