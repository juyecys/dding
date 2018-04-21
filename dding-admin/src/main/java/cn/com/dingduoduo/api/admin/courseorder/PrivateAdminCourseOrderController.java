package cn.com.dingduoduo.api.admin.courseorder;

import cn.com.dingduoduo.api.common.ApiResult;
import cn.com.dingduoduo.entity.channelgroup.ChannelGroupDTO;
import cn.com.dingduoduo.entity.common.Page;
import cn.com.dingduoduo.entity.courseorder.CourseOrder;
import cn.com.dingduoduo.entity.courseorder.CourseOrderDTO;
import cn.com.dingduoduo.service.courseorder.CourseOrderService;
import cn.com.dingduoduo.utils.common.DateUtils;
import cn.com.dingduoduo.utils.common.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
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
        if (StringUtil.isEmpty(courseOrder.getStatus())) {
            courseOrder.setStatus(null);
        }
        if (courseOrder.getCreatedDateEnd() != null) {
            courseOrder.setCreatedDateEnd(DateUtils.add(courseOrder.getCreatedDateEnd(), Calendar.DAY_OF_MONTH, 1));
        }
        Page<CourseOrderDTO> page = courseOrderService.findByConditionPage(courseOrder);
        return new ResponseEntity<>(ApiResult.success(page), HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getList(CourseOrderDTO courseOrder) {
        List<CourseOrderDTO> list = courseOrderService.findByCondition(courseOrder);
        return new ResponseEntity<>(ApiResult.success(list), HttpStatus.OK);
    }

    @RequestMapping(value = "/source", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getAllSource() {
        List<String> list = courseOrderService.getAllSource();
        return new ResponseEntity<>(ApiResult.success(list), HttpStatus.OK);
    }
}
