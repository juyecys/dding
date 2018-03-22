package cn.com.dingduoduo.service.wechat.event.impl;

import cn.com.dingduoduo.entity.common.Event;
import cn.com.dingduoduo.entity.wechat.keyword.WechatTextKeyWord;
import cn.com.dingduoduo.entity.wechat.user.WechatUser;
import cn.com.dingduoduo.entity.wechatuser.LocalWechatUser;
import cn.com.dingduoduo.entity.wechatuser.LocalWechatUserDTO;
import cn.com.dingduoduo.service.wechat.user.WechatUserService;
import cn.com.dingduoduo.service.wechatuser.LocalWechatUserService;
import cn.com.dingduoduo.utils.common.DateUtils;
import cn.com.dingduoduo.utils.common.MapUtils;
import cn.com.dingduoduo.utils.common.StringUtil;
import cn.com.dingduoduo.entity.channel.ChannelDTO;
import cn.com.dingduoduo.service.channel.ChannelService;
import cn.com.dingduoduo.service.event.EventService;
import cn.com.dingduoduo.service.event.EventServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import cn.com.dingduoduo.contants.wechat.WechatConfigParams;
import cn.com.dingduoduo.contants.wechat.WechatEventConstant;
import cn.com.dingduoduo.entity.wechat.event.WechatBaseEvent;
import cn.com.dingduoduo.entity.wechat.event.WechatMenuClickEvent;
import cn.com.dingduoduo.entity.wechat.event.WechatScanEvent;
import cn.com.dingduoduo.entity.wechat.event.WechatSubscribeEvent;
import cn.com.dingduoduo.entity.wechat.localwechatmenu.LocalWechatMenu;
import cn.com.dingduoduo.service.wechat.event.WechatEventService;
import cn.com.dingduoduo.service.wechat.localMenu.LocalWechatMenuService;
import cn.com.dingduoduo.service.wechat.message.WechatMessageService;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by jeysine on 2018/1/24.
 */
@Service("wechatEventService")
public class WechatEventServiceImpl implements WechatEventService {

    @Autowired
    private LocalWechatMenuService localWechatMenuService;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private EventService eventService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private LocalWechatUserService localWechatUserService;

    @Autowired
    private WechatUserService wechatUserService;

    private Logger logger = LoggerFactory.getLogger(WechatEventServiceImpl.class);

    @Override
    public void processEvent(HashMap<String, Object> data) throws Exception {
        String msgType = (String) data.get("MsgType");
        logger.debug("处理微信事件 data: {}",data);
        WechatBaseEvent.MsgTypeEnum msgTypeEnum = WechatBaseEvent.MsgTypeEnum.getEnumByValue(msgType);
        switch (msgTypeEnum) {
            case EVENT: handleWechatEvent(data); break;
            case TEXT: handleTextKeyWord(data); break;
            default: logger.warn("not exist wechat msgType");
        }

    }

    @Override
    public void processSubscribeEvent(WechatSubscribeEvent subscribeEvent) throws Exception {

        logger.debug("处理关注事件 subscribeEvent: {}",subscribeEvent);

        String eventKey = subscribeEvent.getEventKey();
        if (!StringUtil.isEmpty(eventKey)) {
            eventKey = eventKey.replace(WechatConfigParams.WECHAT_PREFIX_QRCODE_EVENT_KEY, "");
        }
        Date subscribeTime = DateUtils.toDate(subscribeEvent.getCreateTime().longValue());

        createOrUpdateWechatUser(subscribeEvent.getFromUserName(), subscribeTime, eventKey);

        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    handleSubscribeMessage(subscribeEvent.getFromUserName(), subscribeEvent.getEventKey());
                } catch (Exception e) {
                    logger.error("error: {}",e);
                }

            }
        });
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

    private void handleSubscribeMessage(String openId, String eventKey) throws IOException {
        if (!StringUtil.isEmpty(eventKey)) {
            eventKey = eventKey.replace(WechatConfigParams.WECHAT_PREFIX_QRCODE_EVENT_KEY, "");
        }

        if (!StringUtil.isEmpty(eventKey)) {
            ChannelDTO channel = new ChannelDTO();
            channel.setScene(eventKey);
            channel = channelService.findOneByCondition(channel);
            if (channel == null) {
                logger.error("not find this channel qrcode, scene: {}", eventKey);
                return;
            }
            if (channel.getSendSubscribeMessage()) {
                wechatMessageService.pushSubscribeMessage(openId);
            }
            if (channel.getSendChannelMessage()) {
                wechatMessageService.pushChannelsMessage(openId, eventKey);
            }
        } else {
            wechatMessageService.pushSubscribeMessage(openId);
        }
    }

    @Override
    public void processUnSubscribeEvent(WechatSubscribeEvent unsubscribeEvent) throws EventServiceException {
        logger.debug("发布取消关注事件 subscribeEvent: {}",unsubscribeEvent);
        Event event = new Event();
        event.addProperty("openId", unsubscribeEvent.getFromUserName());
        event.addProperty("createTime", unsubscribeEvent.getCreateTime());
        event.setType(WechatEventConstant.EVENT_TYPE_WECHAT_USER_UNSUBSCRIBE);
        eventService.publish(event);
    }

    @Override
    public void processScanEvent(WechatScanEvent scanEvent) throws Exception {
        logger.debug("处理扫描二维码事件 scanEvent: {}",scanEvent);
        String eventKey = scanEvent.getEventKey();
        if (!StringUtil.isEmpty(eventKey)) {
            eventKey = eventKey.replace(WechatConfigParams.WECHAT_PREFIX_QRCODE_EVENT_KEY, "");
        }

        ChannelDTO channel = new ChannelDTO();
        channel.setScene(eventKey);
        channel = channelService.findOneByCondition(channel);

        if (channel == null) {
            logger.error("not find this channel qrcode, scene: {}", eventKey);
            return;
        }
        logger.debug("find channel: {}", channel);
        try {
            if (channel.getSendChannelMessage()) {
                wechatMessageService.pushChannelsMessage(scanEvent.getFromUserName(), eventKey);
            }
        } catch (IOException e) {
            logger.error("error: {}",e);
        }
    }

    @Override
    public void processMenuClickEvent(WechatMenuClickEvent menuEvent) throws Exception {
        logger.debug("处理菜单点击事件 scanEvent: {}",menuEvent);
        LocalWechatMenu menu = new LocalWechatMenu();
        menu.setKey(menuEvent.getEventKey());

        menu = localWechatMenuService.findOneByCondition(menu);

        if (menu == null) {
            logger.warn("not found this cn.com.dingduoduo.api.admin.wechat menu key: {}", menuEvent.getEventKey());
            throw new Exception("not found this cn.com.dingduoduo.api.admin.wechat menu");
        }

        switch (menu.getType()) {
            case "article":
                wechatMessageService.pushNewsMessageByMenuEvent(menuEvent.getFromUserName(), menu);
                break;
            case "text":
                wechatMessageService.pushTextMessageByMenuEvent(menuEvent.getFromUserName(), menu);
                break;
        }
    }

    private void handleWechatEvent(HashMap<String, Object> data) throws Exception {
        String event = (String) data.get("Event");
        if (WechatBaseEvent.EventEnum.SUBSCRIBE.getValue().equals(event)) {
            WechatSubscribeEvent subscribeEvent = (WechatSubscribeEvent) MapUtils.getObject(data, WechatSubscribeEvent.class, MapUtils.FirstOneCaseEnum.UPPER);
            processSubscribeEvent(subscribeEvent);
        } else if (WechatBaseEvent.EventEnum.UN_SUBSCRIBE.getValue().equals(event)) {
            WechatSubscribeEvent unSubscribeEvent = (WechatSubscribeEvent) MapUtils.getObject(data, WechatSubscribeEvent.class, MapUtils.FirstOneCaseEnum.UPPER);
            processUnSubscribeEvent(unSubscribeEvent);
        } else if (WechatBaseEvent.EventEnum.SCAN.getValue().equals(event)) {
            WechatScanEvent scanEvent = (WechatScanEvent) MapUtils.getObject(data, WechatScanEvent.class, MapUtils.FirstOneCaseEnum.UPPER);
            processScanEvent(scanEvent);
        } else if (WechatBaseEvent.EventEnum.CLICK.getValue().equals(event)) {
            WechatMenuClickEvent menuClickEvent = (WechatMenuClickEvent) MapUtils.getObject(data, WechatMenuClickEvent.class, MapUtils.FirstOneCaseEnum.UPPER);
            processMenuClickEvent(menuClickEvent);
        }
    }

    private void handleTextKeyWord(HashMap<String, Object> data) throws Exception {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    WechatTextKeyWord wechatTextKeyWord = (WechatTextKeyWord) MapUtils.getObject(data, WechatTextKeyWord.class, MapUtils.FirstOneCaseEnum.UPPER);
                    wechatMessageService.pushKeyWordMessage(wechatTextKeyWord.getFromUserName(), wechatTextKeyWord.getContent());
                } catch (Exception e) {
                    logger.error("error: {}",e);
                }

            }
        });
    }

    public static void main(String[] args) {


    }
}
