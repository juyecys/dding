package cn.com.dingduoduo.api.wechat.event;

import cn.com.dingduoduo.entity.courseorder.CourseOrder;
import cn.com.dingduoduo.entity.courseorder.CourseOrderDTO;
import cn.com.dingduoduo.service.courseorder.CourseOrderService;
import cn.com.dingduoduo.utils.common.Dom4jUtils;
import cn.com.dingduoduo.service.wechat.event.WechatEventService;
import cn.com.dingduoduo.untils.wechat.WechatSignUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;


@RestController
@RequestMapping(value = { "/ding/wechat/public/event" }, produces = "application/json")
public class PublicWechatEventController {


    @Autowired
    private WechatEventService wechatEventService;

    @Autowired
    private CourseOrderService courseOrderService;

    private static final String WX_PAYMENT_RESPONSE_MALFORMAT = "<xml><return_code><![CDATA[FAIL]]></return_code>"
            + "<return_msg><![CDATA[Invalid data format!]]></return_msg></xml>";
    private static final String WX_PAYMENT_RESPONSE_INVALID_ORDER = "<xml><return_code><![CDATA[FAIL]]></return_code>"
            + "<return_msg><![CDATA[Invalid Payment!]]></return_msg></xml>";
    private static final String WX_PAYMENT_RESPONSE_SUCCESS = "<xml><return_code><![CDATA[SUCCESS]]></return_code>"
            + "<return_msg><![CDATA[OK]]></return_msg></xml>";

    private static XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));

    private static Logger logger = LoggerFactory.getLogger(PublicWechatEventController.class);

    static {
        xStream.ignoreUnknownElements();//忽略多余的xml节点

        xStream.alias("xml", WechatPayNotify.class);
        xStream.aliasField("mch_id", WechatPayNotify.class, "mchId");
        xStream.aliasField("device_info", WechatPayNotify.class, "deviceInfo");
        xStream.aliasField("nonce_str", WechatPayNotify.class, "nonceStr");
        xStream.aliasField("sign_type", WechatPayNotify.class, "signType");

        xStream.aliasField("return_msg", WechatPayNotify.class, "returnMsg");
        xStream.aliasField("return_code", WechatPayNotify.class, "returnCode");
        xStream.aliasField("err_code", WechatPayNotify.class, "errCode");
        xStream.aliasField("err_code_des", WechatPayNotify.class, "errCodeDes");
        xStream.aliasField("is_subscribe", WechatPayNotify.class, "isSubscribe");
        xStream.aliasField("trade_type", WechatPayNotify.class, "tradeType");
        xStream.aliasField("settlement_total_fee", WechatPayNotify.class, "settlementTotalFee");
        xStream.aliasField("transaction_id", WechatPayNotify.class, "transactionId");
        xStream.aliasField("out_trade_no", WechatPayNotify.class, "outTradeNo");
        xStream.aliasField("time_end", WechatPayNotify.class, "timeEnd");
    }

    @RequestMapping(value = "/", method = RequestMethod.POST,  produces = "application/xml")
    public void receiveMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 接收参数微信加密签名、 时间戳、随机数
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");

        InputStream inputStream = request.getInputStream();

        PrintWriter out = response.getWriter();

        try {
            HashMap<String, Object> data = Dom4jUtils.readXml(inputStream);
            wechatEventService.processEvent(data);
            logger.debug("微信事件处理结束");
            out.print("");
        } catch (DocumentException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error("read xml error: {}", e);
        } catch (Exception e) {
            logger.error("error: {}", e);
        }
        out.close();
        out = null;
    }


    @RequestMapping(value = "/", method = RequestMethod.GET,  produces = "application/json")
    public void receive(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        // 请求校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (WechatSignUtil.checkUserMessageSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
        out.close();
        out = null;
    }

    /**
     * 微信回调支付
     * @param xmlBody
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public String wechatPayNotify(@RequestBody String xmlBody) throws Exception {
        logger.debug("wechat pay notify: {}", xmlBody);
        WechatPayNotify wechatPayNotify = (WechatPayNotify) xStream.fromXML(xmlBody);
        if (!isPaymentSuccess(wechatPayNotify)) {
            return WX_PAYMENT_RESPONSE_SUCCESS;
        }
        CourseOrderDTO courseOrder = new CourseOrderDTO();
        courseOrder.setOpenId(wechatPayNotify.getOpenid());
        courseOrder.setOrderNumber(wechatPayNotify.getOutTradeNo());
        courseOrder = courseOrderService.findOneByCondition(courseOrder);
        if (courseOrder != null) {
            courseOrder.setStatus(CourseOrder.CourseOrderStatusEnum.PAID.name());
            courseOrderService.createOrUpdate(courseOrder);
        } else {
            logger.error("not exit order, openid: {}, orderNumber", wechatPayNotify.getOpenid(), wechatPayNotify.getOutTradeNo());
            return WX_PAYMENT_RESPONSE_INVALID_ORDER;
        }
        return WX_PAYMENT_RESPONSE_SUCCESS;
    }

    public static boolean isPaymentSuccess(WechatPayNotify notif) {
        String returnCode = notif.getReturnCode();
        String resultCode = notif.getResultCode();

        if (returnCode.equals(ReturnCodeEnum.SUCCESS.name())
                || (returnCode.equals(ReturnCodeEnum.SUCCESS.name()) && resultCode.equals(ReturnCodeEnum.FAIL.name()))) {
            return false;
        }

        return true;
    }

    public class WechatPayNotify {

        private String returnCode;
        private String returnMsg;

        private String appid;
        private String mchId;
        private String deviceInfo;
        private String nonceStr;
        private String sign;
        private String signType;
        private String resultCode;
        private String errCode;
        private String errCodeDes;
        private String openid;
        private String isSubscribe;
        private String tradeType;
        private String bankType;
        private String totalFee;
        private String settlementTotalFee;
        private String transactionId;
        private String outTradeNo;
        private String timeEnd;

        public String getReturnCode() {
            return returnCode;
        }

        public void setReturnCode(String returnCode) {
            this.returnCode = returnCode;
        }

        public String getReturnMsg() {
            return returnMsg;
        }

        public void setReturnMsg(String returnMsg) {
            this.returnMsg = returnMsg;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getMchId() {
            return mchId;
        }

        public void setMchId(String mchId) {
            this.mchId = mchId;
        }

        public String getDeviceInfo() {
            return deviceInfo;
        }

        public void setDeviceInfo(String deviceInfo) {
            this.deviceInfo = deviceInfo;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getSignType() {
            return signType;
        }

        public void setSignType(String signType) {
            this.signType = signType;
        }

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public String getErrCode() {
            return errCode;
        }

        public void setErrCode(String errCode) {
            this.errCode = errCode;
        }

        public String getErrCodeDes() {
            return errCodeDes;
        }

        public void setErrCodeDes(String errCodeDes) {
            this.errCodeDes = errCodeDes;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getIsSubscribe() {
            return isSubscribe;
        }

        public void setIsSubscribe(String isSubscribe) {
            this.isSubscribe = isSubscribe;
        }

        public String getTradeType() {
            return tradeType;
        }

        public void setTradeType(String tradeType) {
            this.tradeType = tradeType;
        }

        public String getBankType() {
            return bankType;
        }

        public void setBankType(String bankType) {
            this.bankType = bankType;
        }

        public String getTotalFee() {
            return totalFee;
        }

        public void setTotalFee(String totalFee) {
            this.totalFee = totalFee;
        }

        public String getSettlementTotalFee() {
            return settlementTotalFee;
        }

        public void setSettlementTotalFee(String settlementTotalFee) {
            this.settlementTotalFee = settlementTotalFee;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getOutTradeNo() {
            return outTradeNo;
        }

        public void setOutTradeNo(String outTradeNo) {
            this.outTradeNo = outTradeNo;
        }

        public String getTimeEnd() {
            return timeEnd;
        }

        public void setTimeEnd(String timeEnd) {
            this.timeEnd = timeEnd;
        }

        @Override
        public String toString() {
            return "WechatPayNotify{" +
                    "returnCode='" + returnCode + '\'' +
                    ", returnMsg='" + returnMsg + '\'' +
                    ", appid='" + appid + '\'' +
                    ", mchId='" + mchId + '\'' +
                    ", deviceInfo='" + deviceInfo + '\'' +
                    ", nonceStr='" + nonceStr + '\'' +
                    ", sign='" + sign + '\'' +
                    ", signType='" + signType + '\'' +
                    ", resultCode='" + resultCode + '\'' +
                    ", errCode='" + errCode + '\'' +
                    ", errCodeDes='" + errCodeDes + '\'' +
                    ", openid='" + openid + '\'' +
                    ", isSubscribe='" + isSubscribe + '\'' +
                    ", tradeType='" + tradeType + '\'' +
                    ", bankType='" + bankType + '\'' +
                    ", totalFee='" + totalFee + '\'' +
                    ", settlementTotalFee='" + settlementTotalFee + '\'' +
                    ", transactionId='" + transactionId + '\'' +
                    ", outTradeNo='" + outTradeNo + '\'' +
                    ", timeEnd='" + timeEnd + '\'' +
                    '}';
        }
    }

    public enum ReturnCodeEnum {
        SUCCESS, FAIL;
    }

}
