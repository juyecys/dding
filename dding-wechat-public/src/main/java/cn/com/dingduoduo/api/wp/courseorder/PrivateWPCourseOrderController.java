package cn.com.dingduoduo.api.wp.courseorder;

import cn.com.dingduoduo.api.common.ApiResult;
import cn.com.dingduoduo.contants.wp.WechatPublicContants;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * Created by jeysine on 2018/4/18.
 */
@RestController
@RequestMapping(value = {"/ding/wp/private/courseorder", "/ding/wp/public/courseorder"}, produces = "application/json")
public class PrivateWPCourseOrderController {

    @Autowired
    private CourseOrderService courseOrderService;

    private static final Logger logger = LoggerFactory.getLogger(PrivateWPCourseOrderController.class);

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> saveOrUpdate(HttpServletRequest request, @RequestBody CourseOrder courseOrder) throws Exception {
        String openId = (String) request.getSession().getAttribute(WechatPublicContants.SESSION_OPENID);
        courseOrder.setOpenId(openId);
        courseOrder = courseOrderService.createOrUpdate(courseOrder);
        return new ResponseEntity<>(ApiResult.success(courseOrder), HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getList(CourseOrderDTO courseOrder) {
        List<CourseOrderDTO> list = courseOrderService.findByCondition(courseOrder);
        return new ResponseEntity<>(ApiResult.success(list), HttpStatus.OK);
    }

    public static void main(String[] args) {
        System.out.println("10EF14E809B7430A81386DC375D73275".length());
        System.out.println(UUID.randomUUID().toString().toUpperCase());
    }
}
