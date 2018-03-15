package cn.com.dingduoduo.dao.resource;

import cn.com.dingduoduo.dao.common.BaseDAO;
import cn.com.dingduoduo.entity.resource.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ResourceDAO extends BaseDAO<Resource, Resource> {
    List<Resource> findResourceByRoleName(@Param("roleName") String roleName);
}
