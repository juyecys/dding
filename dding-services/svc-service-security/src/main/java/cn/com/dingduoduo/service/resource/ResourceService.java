package cn.com.dingduoduo.service.resource;

import cn.com.dingduoduo.entity.resource.Resource;
import cn.com.dingduoduo.service.common.BaseService;

import java.util.List;

public interface ResourceService extends BaseService<Resource, Resource> {
    List<Resource> findResourceByRoleName(String name);
}
