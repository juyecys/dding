package cn.com.dingduoduo.service.mongo.common;

import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by jeysine on 2018/2/9.
 */
public interface BaseMongoService<M, QM extends M> {
    M create(M entity);

    List<QM> findAll();

    List<QM> findAll(Sort var1);

    void delete(String id);
}
