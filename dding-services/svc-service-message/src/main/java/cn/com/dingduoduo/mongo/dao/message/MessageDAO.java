package cn.com.dingduoduo.mongo.dao.message;

import cn.com.dingduoduo.entity.message.Message;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jeysine on 2018/2/9.
 */
@Component
public interface MessageDAO extends PagingAndSortingRepository<Message, String> {
    List<Message> findByStatusOrderBySequence(Boolean status);

    List<Message> findByTypeAndQrCodeScene(String type, String qrCodeScene);

    List<Message> findByStatusAndTypeOrderBySequence(Boolean status, String type);

    List<Message> findByStatusAndTypeAndQrCodeSceneOrderBySequence(Boolean status, String type, String qrCodeScene);

    List<Message> findByStatusAndKeyWordOrderBySequence(Boolean status, String keyWordId);
}
