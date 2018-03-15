package cn.com.dingduoduo.service.common;

import cn.com.dingduoduo.entity.common.Base;
import cn.com.dingduoduo.entity.common.Page;

import java.util.List;


/**
 * Created by Administrator on 2018/1/17.
 */
public interface BaseService<M extends Base,QM extends M> {
    M create(M entity);

    M update(M entity);

    M createOrUpdate(M entity) throws Exception;

    void deleteById(String id);

    List<QM> findByCondition(QM qm);

    Page<QM> findByConditionPage(QM qm);

    QM findOneByCondition(QM qm);
}

