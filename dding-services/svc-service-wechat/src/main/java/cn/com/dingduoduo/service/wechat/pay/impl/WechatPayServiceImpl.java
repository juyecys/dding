package cn.com.dingduoduo.service.wechat.pay.impl;

import cn.com.dingduoduo.config.wechat.WechatConfigSecret;
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

    @Override
    public WechatPayment initPayment(String deviceInfo, String body, String orderNumber, BigDecimal orderFee, String ip,
                                     String tradeType, String productId, String openId) throws Exception {

        WechatInitPayment wechatInitPayment = getWechatInitPayment(deviceInfo, body, orderNumber, orderFee, ip,
                tradeType, productId, openId);

        WechatInitPaymentResult wechatPaymentResult = initPayment(wechatInitPayment);

        WechatPayment wechatPayment = getWechatPayment(wechatPaymentResult.getPrepayId());
        return wechatPayment;
    }

    private WechatInitPayment getWechatInitPayment(String deviceInfo, String body, String orderNumber, BigDecimal orderFee, String ip,
                                                   String tradeType, String productId, String openId) throws Exception {
        WechatInitPayment wechatInitPayment = new WechatInitPayment();
        wechatInitPayment.setAppId(WechatConfigSecret.getWechatAppid());
        wechatInitPayment.setMchId(WechatConfigSecret.getWechatMchId());
        wechatInitPayment.setNonceStr(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());

        wechatInitPayment.setDeviceInfo(deviceInfo);
        wechatInitPayment.setBody(body);
        wechatInitPayment.setOutTradeNo(orderNumber);
        wechatInitPayment.setTotalFee(parseFee(orderFee));
        wechatInitPayment.setSpbillCreateIp(ip);
        wechatInitPayment.setTradeType(tradeType);
        wechatInitPayment.setProductId(productId);
        wechatInitPayment.setOpenId(openId);
        wechatInitPayment.setNotifyUrl(payNotifyUrl);

        Map<String, Object> data = MapUtils.getMap(wechatInitPayment, WechatInitPayment.class);
        wechatInitPayment.setSign(WechatSignUtil.getSign(data, WechatConfigSecret.getWechatPaySecret()));
        return wechatInitPayment;
    }

    private WechatInitPaymentResult initPayment(WechatInitPayment wechatInitPayment) throws Exception {
        String paymentXml = parsePaymentXml(wechatInitPayment);
        logger.debug("init payment to wechat start : {}", paymentXml);
        String result = OkHttpUtils.postString().content(paymentXml).build().execute().body().string();
        logger.debug("init payment to wechat result: {}", result);
        return mapper.readValue(result,WechatInitPaymentResult.class);
    }

    private WechatPayment getWechatPayment(String prepayId) throws Exception {
        WechatPayment wechatPayment = new WechatPayment();
        wechatPayment.setNonceStr(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
        wechatPayment.setAppId(WechatConfigSecret.getWechatAppid());
        wechatPayment.setSignType("MD5");
        wechatPayment.setTimeStamp(String.valueOf(System.currentTimeMillis() / 1000));
        wechatPayment.setPackageStr("prepay_id=" + prepayId);

        Map<String, Object> data = MapUtils.getMap(wechatPayment, WechatPayment.class);
        wechatPayment.setSign(WechatSignUtil.getSign(data, WechatConfigSecret.getWechatPaySecret()));

        return wechatPayment;
    }

    private Integer parseFee(BigDecimal fee) {
        return fee.multiply(BigDecimal.valueOf(100)).intValue();
    }

    private String parsePaymentXml(WechatInitPayment wechatPayment) {
        xStream.aliasField("appid", WechatInitPayment.class, "appId");
        xStream.aliasField("mch_id", WechatInitPayment.class, "mchId");
        xStream.aliasField("device_info", WechatInitPayment.class, "deviceInfo");
        xStream.aliasField("nonce_str", WechatInitPayment.class, "nonceStr");
        xStream.aliasField("sign_type", WechatInitPayment.class, "signType");
        xStream.aliasField("out_trade_no", WechatInitPayment.class, "outTradeNo");
        xStream.aliasField("total_fee", WechatInitPayment.class, "totalFee");
        xStream.aliasField("spbill_create_ip", WechatInitPayment.class, "spbillCreateIp");
        xStream.aliasField("notify_url", WechatInitPayment.class, "notifyUrl");
        xStream.aliasField("trade_type", WechatInitPayment.class, "tradeType");
        xStream.aliasField("product_id", WechatInitPayment.class, "productId");
        xStream.aliasField("openid", WechatInitPayment.class, "openId");

        return xStream.toXML(wechatPayment);
    }

    public static void main(String[] args) {
        WechatInitPayment wechatInitPayment = new WechatInitPayment();
        wechatInitPayment.setAppId("asdfasdfasf");
        wechatInitPayment.setMchId("asdfasdfasdfas");
        wechatInitPayment.setNonceStr(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());

        wechatInitPayment.setDeviceInfo("WEB");
        wechatInitPayment.setBody("ASDFSDFSF");
        wechatInitPayment.setOutTradeNo("2345345");
        wechatInitPayment.setSpbillCreateIp("120.0.0.1");
        wechatInitPayment.setTradeType("");
        wechatInitPayment.setProductId(null);
        wechatInitPayment.setOpenId("dfgbdfg352t5");
        wechatInitPayment.setNotifyUrl("http://baidu.com");


        xStream.aliasField("appid", WechatInitPayment.class, "appId");
        xStream.aliasField("mch_id", WechatInitPayment.class, "mchId");
        xStream.aliasField("device_info", WechatInitPayment.class, "deviceInfo");
        xStream.aliasField("nonce_str", WechatInitPayment.class, "nonceStr");
        xStream.aliasField("sign_type", WechatInitPayment.class, "signType");
        xStream.aliasField("out_trade_no", WechatInitPayment.class, "outTradeNo");
        xStream.aliasField("total_fee", WechatInitPayment.class, "totalFee");
        xStream.aliasField("spbill_create_ip", WechatInitPayment.class, "spbillCreateIp");
        xStream.aliasField("notify_url", WechatInitPayment.class, "notifyUrl");
        xStream.aliasField("trade_type", WechatInitPayment.class, "tradeType");
        xStream.aliasField("product_id", WechatInitPayment.class, "productId");
        xStream.aliasField("openid", WechatInitPayment.class, "openId");

        System.out.println(xStream.toXML(wechatInitPayment));
    }
}
