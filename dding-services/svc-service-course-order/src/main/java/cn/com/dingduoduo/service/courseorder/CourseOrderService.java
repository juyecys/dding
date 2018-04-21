package cn.com.dingduoduo.service.courseorder;

import cn.com.dingduoduo.entity.courseorder.CourseOrder;
import cn.com.dingduoduo.entity.courseorder.CourseOrderDTO;
import cn.com.dingduoduo.service.common.BaseService;

/**
 * Created by jeysine on 2018/4/18.
 */
public interface CourseOrderService extends BaseService<CourseOrder, CourseOrderDTO> {
    CourseOrder createOrUpdate(CourseOrder entity, String openId) throws Exception;
}
