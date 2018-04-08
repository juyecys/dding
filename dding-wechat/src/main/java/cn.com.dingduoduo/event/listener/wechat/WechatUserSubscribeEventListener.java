package cn.com.dingduoduo.event.listener.wechat;

import cn.com.dingduoduo.contants.wechat.WechatConfigParams;
import cn.com.dingduoduo.contants.wechat.WechatEventConstant;
import cn.com.dingduoduo.entity.common.Event;
import cn.com.dingduoduo.entity.wechat.event.WechatSubscribeEvent;
import cn.com.dingduoduo.entity.wechat.user.WechatUser;
import cn.com.dingduoduo.entity.wechatuser.LocalWechatUser;
import cn.com.dingduoduo.entity.wechatuser.LocalWechatUserDTO;
import cn.com.dingduoduo.listener.EventListener;
import cn.com.dingduoduo.service.channel.ChannelService;
import cn.com.dingduoduo.service.wechat.message.WechatMessageService;
import cn.com.dingduoduo.service.wechat.user.WechatUserService;
import cn.com.dingduoduo.service.wechatuser.LocalWechatUserService;
import cn.com.dingduoduo.utils.common.DateUtils;
import cn.com.dingduoduo.utils.common.MapUtils;
import cn.com.dingduoduo.utils.common.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jeysine on 2018/2/8.
 */
@Component
public class WechatUserSubscribeEventListener implements EventListener{

    private Logger logger = LoggerFactory.getLogger(WechatUserSubscribeEventListener.class);

    @Autowired
    private LocalWechatUserService localWechatUserService;

    @Autowired
    private WechatUserService wechatUserService;

    @Override
    public String getId() {
        return "DING_EVENT_WECHAT_USER_SUBSCRIBE_EVENT";
    }

    @Override
    public void handleEvent(Event event) {
        logger.debug("处理关注事件: {}",event);
        Map<String, Object> properties = event.getProperties();
        String openId = properties.get("openId").toString();
        Date createdTime = DateUtils.toDate(((Integer)properties.get("createTime")).longValue());
        String eventKey = properties.get("eventKey") == null ? null: properties.get("eventKey").toString();
        if (!StringUtil.isEmpty(eventKey)) {
            eventKey = eventKey.replace(WechatConfigParams.WECHAT_PREFIX_QRCODE_EVENT_KEY, "");
        }
        try {
            createOrUpdateWechatUser(openId, createdTime, eventKey);
        } catch (Exception e) {
            logger.error("error: {}",e);
        }
    }

    private LocalWechatUser createOrUpdateWechatUser(String openId, Date createdTime, String qrCodeScene) throws Exception {
        LocalWechatUserDTO user = new LocalWechatUserDTO();
        user.setOpenId(openId);
        LocalWechatUserDTO old = localWechatUserService.findOneByCondition(user);
        WechatUser wechatUser = wechatUserService.getWechatUserInfo(openId, null);
        if (old == null) {
            old = new LocalWechatUserDTO();
            old.setUnionId(wechatUser.getUnionId());
            old.setOpenId(wechatUser.getOpenId());
            old.setCreatedDate(createdTime);
            old.setCreatedBy(wechatUser.getNickname());
        } else {
            old.setUpdatedDate(new Date());
            old.setUpdatedBy(wechatUser.getNickname());
        }
        old.setCity(wechatUser.getCity());
        old.setCountry(wechatUser.getCountry());
        old.setHeadImgUrl(wechatUser.getHeadImgUrl());
        old.setNickName(wechatUser.getNickname());
        old.setProvince(wechatUser.getProvince());
        old.setRemark(wechatUser.getRemark());
        old.setGender(wechatUser.getSex());
        old.setSubscribe(wechatUser.getSubscribe());
        old.setQrCodeScene(qrCodeScene);
        old.setSubscribeTime(createdTime);
        localWechatUserService.createOrUpdate(old);
        return old;
    }

    public static void main(String[] args) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("CreateTime", "1519378340");
        map.put("Event", "subscribe");
        map.put("ToUserName", "gh_a1fc816c0c9a");
        map.put("FromUserName", "opqVDwrWpGfjwW0tTuqpk38rS-hc");
        map.put("MsgType", "event");
        try {
            WechatSubscribeEvent subscribeEvent = (WechatSubscribeEvent) MapUtils.getObject(map, WechatSubscribeEvent.class, MapUtils.FirstOneCaseEnum.UPPER);
            System.out.println(subscribeEvent);

            Event event = new Event();
            event.addProperty("openId", subscribeEvent.getFromUserName());
            event.addProperty("createTime", subscribeEvent.getCreateTime());
            event.addProperty("eventKey", subscribeEvent.getEventKey());
            event.setType(WechatEventConstant.EVENT_TYPE_WECHAT_USER_SUBSCRIBE);
            Map<String, Object> properties = event.getProperties();
            String eventKey = properties.get("eventKey") == null ? null: properties.get("eventKey").toString();

            System.out.println(StringUtil.isEmpty(eventKey));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
