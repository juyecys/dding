package cn.com.dingduoduo.service.courseorder.impl;

import cn.com.dingduoduo.dao.courseorder.CourseOrderDAO;
import cn.com.dingduoduo.entity.courseorder.CourseOrder;
import cn.com.dingduoduo.entity.courseorder.CourseOrderDTO;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import cn.com.dingduoduo.service.courseorder.CourseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
