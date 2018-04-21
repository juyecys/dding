package cn.com.dingduoduo.service.wechat.pay.impl;

import cn.com.dingduoduo.config.wechat.WechatConfigSecret;
import cn.com.dingduoduo.contants.wechat.WechatConfigParams;
import cn.com.dingduoduo.entity.wechat.pay.WechatInitPayment;
import cn.com.dingduoduo.entity.wechat.pay.WechatInitPaymentResult;
import cn.com.dingduoduo.entity.wechat.pay.WechatPayment;
import cn.com.dingduoduo.service.wechat.pay.WechatPayService;
import cn.com.dingduoduo.untils.wechat.WechatSignUtil;
import cn.com.dingduoduo.utils.common.MapUtils;
import cn.com.dingduoduo.utils.common.okhttputil.OkHttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

/**
 * Created by jeysine on 2018/4/19.
 */
@Service("wechatPayService")
public class WechatPayServiceImpl implements WechatPayService {

    @Value("${wechat.dingduoduo.wechat.pay.notify.url}")
    private String payNotifyUrl;

    private static XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));

    private static final ObjectMapper mapper = new ObjectMapper();

    private Logger logger = LoggerFactory.getLogger(WechatPayServiceImpl.class);

    static {
        xStream.ignoreUnknownElements();//忽略多余的xml节点

        xStream.alias("xml", WechatInitPayment.class);

        xStream.alias("xml", WechatInitPaymentResult.class);
        xStream.aliasField("result_code", WechatInitPaymentResult.class, "resultCode");
        xStream.aliasField("return_msg", WechatInitPaymentResult.class, "returnMsg");
        xStream.aliasField("return_code", WechatInitPaymentResult.class, "returnCode");
        xStream.aliasField("err_code", WechatInitPaymentResult.class, "errCode");
        xStream.aliasField("err_code_des", WechatInitPaymentResult.class, "errCodeDes");
        xStream.aliasField("prepay_id", WechatInitPaymentResult.class, "prepayId");
        xStream.aliasField("code_url", WechatInitPaymentResult.class, "codeUrl");
    }

    @Transactional
    @Override
    public WechatPayment initPayment(String deviceInfo, String body, String orderNumber, BigDecimal orderFee, String ip,
                                     String tradeType, String productId, String openId) throws Exception {

        WechatInitPayment wechatInitPayment = getWechatInitPayment(deviceInfo, body, orderNumber, orderFee, ip,
                tradeType, productId, openId);

        WechatInitPaymentResult wechatPaymentResult = initPayment(wechatInitPayment);

        WechatPayment wechatPayment = getWechatPayment(wechatPaymentResult.getPrepayId());
        logger.debug("wechat payment: {}", wechatPayment);
        return wechatPayment;
    }

    private WechatInitPayment getWechatInitPayment(String deviceInfo, String body, String orderNumber, BigDecimal orderFee, String ip,
                                                   String tradeType, String productId, String openId) throws Exception {
        WechatInitPayment wechatInitPayment = new WechatInitPayment();
        wechatInitPayment.setAppid(WechatConfigSecret.getWechatAppid());
        wechatInitPayment.setMch_id(WechatConfigSecret.getWechatMchId());
        wechatInitPayment.setNonce_str(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());

        wechatInitPayment.setDevice_info(deviceInfo);
        wechatInitPayment.setBody(body);
        wechatInitPayment.setOut_trade_no(orderNumber);
        wechatInitPayment.setTotal_fee(parseFee(orderFee));
        wechatInitPayment.setSpbill_create_ip(ip);
        wechatInitPayment.setTrade_type(tradeType);
        wechatInitPayment.setProduct_id(productId);
        wechatInitPayment.setOpenid(openId);
        wechatInitPayment.setNotify_url(payNotifyUrl);

        Map<String, Object> data = MapUtils.getMap(wechatInitPayment, WechatInitPayment.class);
        wechatInitPayment.setSign(WechatSignUtil.getSign(data, WechatConfigSecret.getWechatPaySecret()));
        return wechatInitPayment;
    }

    private WechatInitPaymentResult initPayment(WechatInitPayment wechatInitPayment) throws Exception {
        String paymentXml = parsePaymentXml(wechatInitPayment);
        logger.debug("init payment to wechat start : {}", paymentXml);
        String result = OkHttpUtils.postString().url(WechatConfigParams.WECHAT_PAY_URL).content(paymentXml).build().execute().body().string();
        logger.debug("init payment to wechat result: {}", result);
        return (WechatInitPaymentResult) xStream.fromXML(result);
    }

    private WechatPayment getWechatPayment(String prepayId) throws Exception {
        WechatPayment wechatPayment = new WechatPayment();
        wechatPayment.setNonceStr(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
        wechatPayment.setAppId(WechatConfigSecret.getWechatAppid());
        wechatPayment.setSignType("MD5");
        wechatPayment.setTimeStamp(String.valueOf(System.currentTimeMillis() / 1000));
        wechatPayment.setPackageStr("prepay_id=" + prepayId);

        Map<String, Object> data = MapUtils.getMap(wechatPayment, WechatPayment.class);
        wechatPayment.setPaySign(WechatSignUtil.getSign(data, WechatConfigSecret.getWechatPaySecret()));

        return wechatPayment;
    }

    private Integer parseFee(BigDecimal fee) {
        return fee.multiply(BigDecimal.valueOf(100)).intValue();
    }

    private String parsePaymentXml(WechatInitPayment wechatPayment) {
        return xStream.toXML(wechatPayment);
    }

    public static void main(String[] args) {
        String xml = "<xml><return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "<return_msg><![CDATA[OK]]></return_msg>\n" +
                "<appid><![CDATA[wxdc730562494e6fc1]]></appid>\n" +
                "<mch_id><![CDATA[1260740101]]></mch_id>\n" +
                "<device_info><![CDATA[WEB]]></device_info>\n" +
                "<nonce_str><![CDATA[JmWPsz6Htf5vOryi]]></nonce_str>\n" +
                "<sign><![CDATA[03F11A0D14FF7602505DFE554E77D9DE]]></sign>\n" +
                "<result_code><![CDATA[SUCCESS]]></result_code>\n" +
                "<prepay_id><![CDATA[wx2019070366091296db08ffd00228037528]]></prepay_id>\n" +
                "<trade_type><![CDATA[JSAPI]]></trade_type>\n" +
                "</xml>";
        WechatInitPaymentResult wechatInitPaymentResult = (WechatInitPaymentResult) xStream.fromXML(xml);
        System.out.println(wechatInitPaymentResult);
    }
}
