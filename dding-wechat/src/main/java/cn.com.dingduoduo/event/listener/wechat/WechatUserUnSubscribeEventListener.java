package cn.com.dingduoduo.event.listener.wechat;

import cn.com.dingduoduo.contants.wechat.WechatEventConstant;
import cn.com.dingduoduo.entity.common.Event;
import cn.com.dingduoduo.entity.wechat.event.WechatSubscribeEvent;
import cn.com.dingduoduo.entity.wechatuser.LocalWechatUser;
import cn.com.dingduoduo.entity.wechatuser.LocalWechatUserDTO;
import cn.com.dingduoduo.listener.EventListener;
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
public class WechatUserUnSubscribeEventListener implements EventListener{

    private Logger logger = LoggerFactory.getLogger(WechatUserUnSubscribeEventListener.class);

    @Autowired
    private LocalWechatUserService localWechatUserService;


    @Override
    public String getId() {
        return "YKB_EVENT_WECHAT_USER_SUBSCRIBE_EVENT";
    }

    @Override
    public void handleEvent(Event event) {
        logger.debug("处理取消关注事件: {}",event);
        Map<String, Object> properties = event.getProperties();
        String openId = properties.get("openId").toString();
        Date createdTime = DateUtils.toDate(((Integer)properties.get("createTime")).longValue());

        try {
            LocalWechatUserDTO user = new LocalWechatUserDTO();
            user.setOpenId(openId);
            user = localWechatUserService.findOneByCondition(user);
            if (user != null) {
                LocalWechatUser wechatUser = new LocalWechatUser();
                wechatUser.setId(user.getId());
                wechatUser.setUpdatedDate(createdTime);
                wechatUser.setUpdatedBy(user.getNickName());
                wechatUser.setSubscribe(0);
                localWechatUserService.createOrUpdate(wechatUser);
            }
        } catch (Exception e) {
            logger.error("error: {}",e);
        }
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
