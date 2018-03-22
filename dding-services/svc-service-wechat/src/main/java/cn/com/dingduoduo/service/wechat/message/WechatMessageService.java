package cn.com.dingduoduo.service.wechat.message;


import cn.com.dingduoduo.entity.message.Message;
import cn.com.dingduoduo.entity.wechat.localwechatmenu.LocalWechatMenu;
import cn.com.dingduoduo.entity.wechat.message.WechatCustomMessage;
import cn.com.dingduoduo.entity.wechat.message.WechatTemplateMessage;
import cn.com.dingduoduo.entity.wechat.result.WechatCommonResult;

import java.io.IOException;
import java.util.List;

/**
 * Created by jeysine on 2018/1/25.
 */
public interface WechatMessageService {
    WechatCommonResult pushMessage(WechatCustomMessage wechatCustomMessage) throws IOException;

    WechatCommonResult pushNewsMessageByMenuEvent(String openId, LocalWechatMenu menu) throws IOException;

    WechatCommonResult pushTextMessageByMenuEvent(String openId, LocalWechatMenu menu) throws IOException;

    WechatCommonResult pushNewsMessage(String openId, List<WechatCustomMessage.News.Article> articleList) throws IOException;

    WechatCommonResult pushTextMessage(String openId, String content) throws IOException;

    WechatCommonResult pushImageMessage(String openId, String mediaId) throws IOException;

    WechatCommonResult pushTemplateMessage(WechatTemplateMessage templateMessage) throws IOException;

    void pushMessageByMessage(String openId, Message message) throws IOException;

    void pushMessageListByMessage(String openId, List<Message> message) throws IOException;

    void pushSubscribeMessage(String openid) throws IOException;

    void pushChannelsMessage(String openId, String qrCodeScene) throws IOException;

}
