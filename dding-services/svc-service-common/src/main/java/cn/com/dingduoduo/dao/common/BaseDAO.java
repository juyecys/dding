package cn.com.dingduoduo.dao.common;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseDAO<M,QM>{
	
	void create(M m);

	int update(M m);

	void deleteById(@Param("id") String id);

	List<QM> findByCondition(QM qm);

	List<QM> findByConditionPage(QM qm);

	QM findOneByCondition(QM qm);

}
