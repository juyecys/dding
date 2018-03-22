package cn.com.dingduoduo.service.message;


import cn.com.dingduoduo.entity.message.Message;
import cn.com.dingduoduo.service.mongo.common.BaseMongoService;

import java.util.List;

/**
 * Created by jeysine on 2018/2/9.
 */
public interface MessageService extends BaseMongoService<Message, Message> {

    Message createOrUpdate(Message message);

    List<Message> findByStatusOrderBySequence(Boolean status);

    List<Message> findByTypeAndQrCodeScene(String type, String qrCodeScene);

    List<Message> findByStatusAndTypeOrderBySequence(Boolean status, String type);

    List<Message> findByStatusAndKeyWordIdOrderBySequence(Boolean status, String keyWordId);

    List<Message> findByStatusAndTypeAndQrCodeSceneOrderBySequence(Boolean status, String type, String qrCodeScene);


}
