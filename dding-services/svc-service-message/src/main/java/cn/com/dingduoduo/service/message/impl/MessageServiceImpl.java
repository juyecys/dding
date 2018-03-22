package cn.com.dingduoduo.service.message.impl;

import cn.com.dingduoduo.entity.message.Message;
import cn.com.dingduoduo.mongo.dao.message.MessageDAO;
import cn.com.dingduoduo.service.message.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by jeysine on 2018/2/9.
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDAO dao;

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Override
    public Message createOrUpdate(Message message) {
        logger.debug("save message to mongo: {}", message);
        if (message.getId() == null) {
            message.setId(UUID.randomUUID().toString());
        }
        create(message);
        return message;
    }

    @Override
    public List<Message> findByStatusOrderBySequence(Boolean status) {
        return dao.findByStatusOrderBySequence(status);
    }

    @Override
    public List<Message> findByTypeAndQrCodeScene(String type, String qrCodeScene) {
        return dao.findByTypeAndQrCodeScene(type, qrCodeScene);
    }

    @Override
    public List<Message> findByStatusAndTypeOrderBySequence(Boolean status, String type) {
        return dao.findByStatusAndTypeOrderBySequence(status, type);
    }

    @Override
    public List<Message> findByStatusAndKeyWordOrderBySequence(Boolean status, String keyWordId) {
        return dao.findByStatusAndKeyWordOrderBySequence(status, keyWordId);
    }

    @Override
    public List<Message> findByStatusAndTypeAndQrCodeSceneOrderBySequence(Boolean status, String type, String qrCodeScene) {
        return dao.findByStatusAndTypeAndQrCodeSceneOrderBySequence(status, type, qrCodeScene);
    }

    @Override
    public Message create(Message entity) {
        return dao.save(entity);
    }

    @Override
    public List<Message> findAll() {
        return (List<Message>) dao.findAll();
    }

    @Override
    public List<Message> findAll(Sort var1) {
        return null;
    }

    @Override
    public void delete(String id) {
        dao.delete(id);
    }



}
