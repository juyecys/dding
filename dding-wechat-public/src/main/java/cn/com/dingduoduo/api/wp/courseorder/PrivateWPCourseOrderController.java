package cn.com.dingduoduo.api.wp.courseorder;

import cn.com.dingduoduo.api.common.ApiResult;
import cn.com.dingduoduo.config.common.WechatContextHolder;
import cn.com.dingduoduo.contants.wp.WechatPublicContants;
import cn.com.dingduoduo.entity.courseorder.CourseOrder;
import cn.com.dingduoduo.entity.courseorder.CourseOrderDTO;
import cn.com.dingduoduo.entity.wechat.pay.WechatInitPayment;
import cn.com.dingduoduo.entity.wechat.pay.WechatPayment;
import cn.com.dingduoduo.service.courseorder.CourseOrderService;
import cn.com.dingduoduo.service.wechat.pay.WechatPayService;
import cn.com.dingduoduo.utils.common.HttpServletUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Autowired
    private WechatPayService wechatPayService;

    private static final Logger logger = LoggerFactory.getLogger(PrivateWPCourseOrderController.class);

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> saveOrUpdate(HttpServletRequest request, @RequestBody CourseOrder courseOrder) throws Exception {
        String openId = WechatContextHolder.getOpenId();
        logger.debug("open_id: {}", openId);
        courseOrder = courseOrderService.createOrUpdate(courseOrder, openId);
        String userIp = HttpServletUtils.getRealIp(request);
        /*WechatPayment wechatPayment = wechatPayService.initPayment("WEB", courseOrder.getCourseName(), courseOrder.getOrderNumber(), courseOrder.getPrice(), userIp, WechatInitPayment.TradeTypeEnum.JSAPI.name(),
                null, openId);*/

        WechatPayAndCourseOrder wechatPayAndCourseOrder = new WechatPayAndCourseOrder();
        wechatPayAndCourseOrder.setCourseOrder(courseOrder);
        wechatPayAndCourseOrder.setWechatPayment(new WechatPayment());
        return new ResponseEntity<>(ApiResult.success(wechatPayAndCourseOrder), HttpStatus.OK);
    }

    @RequestMapping(value = "/initpayment", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> initpayment(HttpServletRequest request, @RequestBody CourseOrderDTO courseOrder) throws Exception {
        String openId = WechatContextHolder.getOpenId();
        logger.debug("open_id: {}", openId);
        courseOrder = courseOrderService.findOneByCondition(courseOrder);
        String userIp = HttpServletUtils.getRealIp(request);
        WechatPayment wechatPayment = wechatPayService.initPayment("WEB", courseOrder.getCourseName(), courseOrder.getOrderNumber(), courseOrder.getPrice(), userIp, WechatInitPayment.TradeTypeEnum.JSAPI.name(),
                null, openId);
        return new ResponseEntity<>(ApiResult.success(wechatPayment), HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getList(HttpServletRequest request, CourseOrderDTO courseOrder) {
        String openId = WechatContextHolder.getOpenId();
        logger.debug("open_id: {}", openId);
        if (openId == null) {
            return new ResponseEntity<>(ApiResult.success(), HttpStatus.OK);
        }
        courseOrder.setOpenId(openId);
        courseOrder.setStatus(CourseOrder.CourseOrderStatusEnum.PAID.name());
        List<CourseOrderDTO> list = courseOrderService.findByCondition(courseOrder);
        return new ResponseEntity<>(ApiResult.success(list), HttpStatus.OK);
    }

    public class WechatPayAndCourseOrder {
        @JsonProperty("wechatPayment")
        private WechatPayment wechatPayment;

        @JsonProperty("courseOrder")
        private CourseOrder courseOrder;

        public WechatPayment getWechatPayment() {
            return wechatPayment;
        }

        public void setWechatPayment(WechatPayment wechatPayment) {
            this.wechatPayment = wechatPayment;
        }

        public CourseOrder getCourseOrder() {
            return courseOrder;
        }

        public void setCourseOrder(CourseOrder courseOrder) {
            this.courseOrder = courseOrder;
        }
    }

    public static void main(String[] args) {
        System.out.println("10EF14E809B7430A81386DC375D73275".length());
        System.out.println(UUID.randomUUID().toString().toUpperCase());
    }
}
