package cn.com.dingduoduo.api.admin.wechatuser;

import cn.com.dingduoduo.api.common.ApiResult;
import cn.com.dingduoduo.utils.common.DateUtils;
import cn.com.dingduoduo.entity.common.Page;
import cn.com.dingduoduo.utils.common.StringUtil;
import cn.com.dingduoduo.entity.wechatuser.LocalWechatUserDTO;
import cn.com.dingduoduo.service.wechatuser.LocalWechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = { "/ding/mg/private/wechatuser","/ding/mg/public/wechatuser" }, produces = "application/json")
public class PrivateAdminWechatUserController {
    @Autowired
    private LocalWechatUserService localWechatUserService;

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getUser(LocalWechatUserDTO wechatUser) {
        List<LocalWechatUserDTO> wechatUserList =  localWechatUserService.findByCondition(wechatUser);
        return new ResponseEntity<>(ApiResult.success(wechatUserList), HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getUserPage(@RequestParam(value = "subscribeDateEnd", required = false) String subscribeDateEnd
            ,@RequestParam(value = "subscribeDateStart", required = false) String subscribeDateStart
            ,@RequestParam(value = "channels", required = false) String channels
            ,@RequestParam(value = "channelGroupName", required = false) String channelGroupName
            ,@RequestParam(value = "province", required = false) String province
            ,@RequestParam(value = "city", required = false) String city
            ,@RequestParam(value = "openId", required = false) String openId
            ,@RequestParam(value = "pageSize", required = false) Integer pageSize
            ,@RequestParam(value = "nowPage", required = false) Integer nowPage) {
        LocalWechatUserDTO wechatUser = new LocalWechatUserDTO();
        wechatUser.setChannels(channels);
        wechatUser.setChannelGroupName(channelGroupName);
        wechatUser.setProvince(province);
        wechatUser.setCity(city);
        wechatUser.setOpenId(openId);
        wechatUser.setPage(new Page());
        wechatUser.getPage().setNowPage(nowPage == null? 1: nowPage);
        wechatUser.getPage().setPageSize(pageSize == null? 10: pageSize);
        if (!StringUtil.isEmpty(subscribeDateStart)) {
            wechatUser.setSubscribeDateStart(DateUtils.stringToDate(subscribeDateStart));
        }
        if (!StringUtil.isEmpty(subscribeDateEnd)) {
            Date endDate = DateUtils.stringToDate(subscribeDateEnd);
            wechatUser.setSubscribeDateEnd(DateUtils.add(endDate, Calendar.DAY_OF_MONTH, 1));

        }

        Page<LocalWechatUserDTO> wechatUserList =  localWechatUserService.findByConditionPage(wechatUser);
        return new ResponseEntity<>(ApiResult.success(wechatUserList), HttpStatus.OK);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> countUsers(LocalWechatUserDTO wechatUser) {
        Integer count =  localWechatUserService.countUsers(wechatUser);
        if (count == null) {
            count = 0;
        }
        return new ResponseEntity<>(ApiResult.success(count), HttpStatus.OK);
    }
}
