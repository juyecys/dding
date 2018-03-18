package cn.com.dingduoduo.api.admin.channelgroup;

import cn.com.dingduoduo.api.common.ApiResult;
import cn.com.dingduoduo.entity.channelgroup.ChannelGroup;
import cn.com.dingduoduo.entity.channelgroup.ChannelGroupDTO;
import cn.com.dingduoduo.entity.common.Page;
import cn.com.dingduoduo.service.channelgroup.ChannelGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by jeysine on 2018/2/21.
 */
@RestController
@RequestMapping(value = {"/ding/mg/private/channelgroup", "/ding/mg/public/channelgroup"}, produces = "application/json")
public class PrivateAdminChannelGroupController {
    @Autowired
    private ChannelGroupService channelGroupService;

    private static final Logger logger = LoggerFactory.getLogger(PrivateAdminChannelGroupController.class);

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> saveOrUpdateChannelGroup(@RequestBody ChannelGroup channelGroup) throws Exception {
        channelGroup = channelGroupService.createOrUpdate(channelGroup);
        return new ResponseEntity<>(ApiResult.success(channelGroup), HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getChannelGroup(ChannelGroupDTO channelGroup) {
       Page<ChannelGroupDTO> page = channelGroupService.findByConditionPage(channelGroup);
        return new ResponseEntity<>(ApiResult.success(page), HttpStatus.OK);
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getAllChannelGroup(ChannelGroupDTO channelGroup) {
        List<ChannelGroupDTO> list = channelGroupService.findByCondition(channelGroup);
        return new ResponseEntity<>(ApiResult.success(list), HttpStatus.OK);
    }
}
