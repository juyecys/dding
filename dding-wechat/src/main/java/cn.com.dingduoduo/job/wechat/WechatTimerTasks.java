package cn.com.dingduoduo.job.wechat;

import cn.com.dingduoduo.entity.wechat.user.WechatUser;
import cn.com.dingduoduo.entity.wechat.user.WechatUserDTO;
import cn.com.dingduoduo.entity.wechatuser.LocalWechatUserDTO;
import cn.com.dingduoduo.job.DistributedExclusiveTask;
import cn.com.dingduoduo.service.wechat.user.WechatUserService;
import cn.com.dingduoduo.service.wechatuser.LocalWechatUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Component("partnerTimerTasks")
@DistributedExclusiveTask("wechat-timertasks")
public class WechatTimerTasks {
    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private LocalWechatUserService localWechatUserService;

    private Logger logger = LoggerFactory.getLogger(WechatTimerTasks.class);

    @Scheduled(cron = "0 0/1 * * * ?")
    public void getUserInfo() throws IOException {
        List<LocalWechatUserDTO> localWechatUserDTOList = localWechatUserService.findByUnsynchronous();
        if (!localWechatUserDTOList.isEmpty()) {
            List<String> openIds = new LinkedList<>();
            for (LocalWechatUserDTO one : localWechatUserDTOList) {
                openIds.add(one.getOpenId());
            }
            WechatUserDTO userInfoList = wechatUserService.getWechatUserInfoList(openIds);
            if (userInfoList.getErrmsg() == null && userInfoList.getUserInfoList().isEmpty()) {
                logger.error("get user info list error, error code: {}, error msg: {}", userInfoList.getErrcode(), userInfoList.getErrmsg());
                return;
            }
            List<WechatUser> wechatUsers = userInfoList.getUserInfoList();
            logger.info("wechatUsers: {}", wechatUsers);
            LocalWechatUserDTO wechatUserDTO = new LocalWechatUserDTO();
            for (WechatUser one: wechatUsers) {
                wechatUserDTO.setCity(one.getCity());
                wechatUserDTO.setCountry(one.getCountry());
                wechatUserDTO.setHeadImgUrl(one.getHeadImgUrl());
                wechatUserDTO.setNickName(one.getNickname());
                wechatUserDTO.setProvince(one.getProvince());
                wechatUserDTO.setRemark(one.getRemark());
                wechatUserDTO.setGender(one.getSex());
                wechatUserDTO.setSubscribe(one.getSubscribe());
                wechatUserDTO.setUnionId(one.getUnionId());
                wechatUserDTO.setOpenId(one.getOpenId());
                localWechatUserService.synchronousUser(wechatUserDTO);
            }

        }

    }
}
