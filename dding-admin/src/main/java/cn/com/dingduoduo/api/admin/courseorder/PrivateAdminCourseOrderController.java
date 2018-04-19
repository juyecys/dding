package cn.com.dingduoduo.api.admin.courseorder;

import cn.com.dingduoduo.api.common.ApiResult;
import cn.com.dingduoduo.entity.channelgroup.ChannelGroupDTO;
import cn.com.dingduoduo.entity.common.Page;
import cn.com.dingduoduo.entity.courseorder.CourseOrder;
import cn.com.dingduoduo.entity.courseorder.CourseOrderDTO;
import cn.com.dingduoduo.service.courseorder.CourseOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by jeysine on 2018/4/18.
 */
@RestController
@RequestMapping(value = {"/ding/mg/private/courseorder", "/ding/mg/public/courseorder"}, produces = "application/json")
public class PrivateAdminCourseOrderController {

    @Autowired
    private CourseOrderService courseOrderService;

    private static final Logger logger = LoggerFactory.getLogger(PrivateAdminCourseOrderController.class);

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> saveOrUpdate(@RequestBody CourseOrder courseOrder) throws Exception {
        courseOrder = courseOrderService.createOrUpdate(courseOrder);
        return new ResponseEntity<>(ApiResult.success(courseOrder), HttpStatus.OK);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getPage(@RequestBody CourseOrderDTO courseOrder) {
        Page<CourseOrderDTO> page = courseOrderService.findByConditionPage(courseOrder);
        return new ResponseEntity<>(ApiResult.success(page), HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getList(CourseOrderDTO courseOrder) {
        List<CourseOrderDTO> list = courseOrderService.findByCondition(courseOrder);
        return new ResponseEntity<>(ApiResult.success(list), HttpStatus.OK);
    }
}
