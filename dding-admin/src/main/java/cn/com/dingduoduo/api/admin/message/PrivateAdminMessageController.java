package cn.com.dingduoduo.api.admin.message;

import cn.com.dingduoduo.api.common.ApiResult;
import cn.com.dingduoduo.entity.channel.ChannelDTO;
import cn.com.dingduoduo.entity.message.Message;
import cn.com.dingduoduo.entity.message.MessageDTO;
import cn.com.dingduoduo.service.channel.ChannelService;
import cn.com.dingduoduo.service.message.MessageService;
import cn.com.dingduoduo.service.wechat.message.WechatMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Created by jeysine on 2018/2/9.
 */
@RestController
@RequestMapping(value = { "/ding/mg/private/message", "/ding/mg/public/message" }, produces = "application/json")
public class PrivateAdminMessageController {
    private static Logger logger = LoggerFactory.getLogger(PrivateAdminMessageController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private WechatMessageService wechatMessageServiceRPC;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> createMessage(@RequestBody Message message) {
        message = messageService.createOrUpdate(message);
        return new ResponseEntity<>(ApiResult.success(message), HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getMessage(Message message) {
        List<Message> messageList =  messageService.findByTypeAndQrCodeScene(message.getType(), message.getQrCodeScene());
        return new ResponseEntity<>(ApiResult.success(messageList), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> deleteMessage(@RequestBody Message message) {
        messageService.delete(message.getId());
        return new ResponseEntity<>(ApiResult.success(), HttpStatus.OK);
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> sendMessage(@RequestBody MessageDTO message) throws IOException {
        if (Message.TypeEnum.CHANNEL.name().equals(message.getType())) {
            ChannelDTO channel = new ChannelDTO();
            channel.setScene(message.getQrCodeScene());
            channel = channelService.findOneByCondition(channel);
            if (channel.getSendChannelMessage()) {
                wechatMessageServiceRPC.pushChannelsMessage(message.getOpenId(), message.getQrCodeScene());
            }
            if (channel.getSendSubscribeMessage()) {
                wechatMessageServiceRPC.pushSubscribeMessage(message.getOpenId());
            }

        } else if(Message.TypeEnum.SUBSCRIBE.name().equals(message.getType())){
            wechatMessageServiceRPC.pushSubscribeMessage(message.getOpenId());
        }
        return new ResponseEntity<>(ApiResult.success(), HttpStatus.OK);
    }
}
