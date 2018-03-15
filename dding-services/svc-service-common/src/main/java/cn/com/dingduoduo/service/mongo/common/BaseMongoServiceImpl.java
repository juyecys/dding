package cn.com.dingduoduo.service.mongo.common;

import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by jeysine on 2018/2/9.
 */
public class BaseMongoServiceImpl<M, QM extends M> implements BaseMongoService {
    @Override
    public M create(Object entity) {
        return null;
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public List findAll(Sort var1) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
