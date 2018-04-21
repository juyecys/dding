package cn.com.dingduoduo.service.courseorder.impl;

import cn.com.dingduoduo.dao.courseorder.CourseOrderDAO;
import cn.com.dingduoduo.entity.courseorder.CourseOrder;
import cn.com.dingduoduo.entity.courseorder.CourseOrderDTO;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import cn.com.dingduoduo.service.courseorder.CourseOrderService;
import cn.com.dingduoduo.utils.common.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by jeysine on 2018/4/18.
 */
@Service("courseOrderService")
public class CourseOrderServiceImpl extends BaseServiceImpl<CourseOrder,CourseOrderDTO> implements CourseOrderService {
    @Autowired
    private CourseOrderDAO dao;

    @Autowired
    public void setDao(CourseOrderDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }

    @Override
    public CourseOrder createOrUpdate(CourseOrder entity, String openId) throws Exception {
        if (entity.getPrice() != null) {
            entity.getPrice().setScale(2, BigDecimal.ROUND_DOWN);
        }
        if (entity.getId() == null) {
            CourseOrder courseOrder = checkExistCourseOrder(openId, entity.getCourseId(), CourseOrder.CourseOrderStatusEnum.WAIT_PAID.name());

            if (courseOrder != null) {
                return courseOrder;
            }
            entity.setOpenId(openId);
            entity.setOrderNumber(StringUtil.numRandom(15));
            entity.setStatus(CourseOrder.CourseOrderStatusEnum.WAIT_PAID.name());
            return create(entity);
        }
        return update(entity);
    }

    private CourseOrder checkExistCourseOrder(String openId, String courseId, String status) {
        CourseOrderDTO courseOrder = new CourseOrderDTO();
        courseOrder.setStatus(status);
        courseOrder.setOpenId(openId);
        courseOrder.setCourseId(courseId);
        return findOneByCondition(courseOrder);
    }
}
