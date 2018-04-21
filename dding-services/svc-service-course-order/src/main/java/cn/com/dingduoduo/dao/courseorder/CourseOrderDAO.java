package cn.com.dingduoduo.dao.courseorder;

import cn.com.dingduoduo.dao.common.BaseDAO;
import cn.com.dingduoduo.entity.courseorder.CourseOrder;
import cn.com.dingduoduo.entity.courseorder.CourseOrderDTO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jeysine on 2018/4/18.
 */
@Component
public interface CourseOrderDAO extends BaseDAO<CourseOrder, CourseOrderDTO> {
    List<String> getAllSource();
}
