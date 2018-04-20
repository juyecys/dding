package cn.com.dingduoduo.service.wechat.pay.impl;

import cn.com.dingduoduo.config.wechat.WechatConfigSecret;
import cn.com.dingduoduo.entity.wechat.pay.WechatPayment;
import cn.com.dingduoduo.entity.wechat.pay.WechatPaymentResult;
import cn.com.dingduoduo.service.wechat.pay.WechatPayService;
import cn.com.dingduoduo.untils.wechat.WechatSignUtil;
import cn.com.dingduoduo.utils.common.MapUtils;
import cn.com.dingduoduo.utils.common.XStreamUtil;
import cn.com.dingduoduo.utils.common.okhttputil.OkHttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * Created by jeysine on 2018/4/19.
 */
@Service("wechatPayService")
public class WechatPayServiceImpl implements WechatPayService {

    @Value("${wechat.dingduoduo.wechat.pay.notify.url}")
    private String payNotifyUrl;

    private static XStream xStream = new XStream(new DomDriver());

    private static final ObjectMapper mapper = new ObjectMapper();

    private Logger logger = LoggerFactory.getLogger(WechatPayServiceImpl.class);

    @Override
    public WechatPaymentResult initPayment(String deviceInfo, String body, String attach, String orderNumber, Integer orderFee, String ip,
                                     String tradeType, String productId, String openId) throws Exception {
        WechatPayment wechatPayment = new WechatPayment();
        wechatPayment.setAppId(WechatConfigSecret.getWechatAppid());
        wechatPayment.setMchId(WechatConfigSecret.getWechatMchId());
        wechatPayment.setNonceStr(UUID.randomUUID().toString());

        wechatPayment.setDeviceInfo(deviceInfo);
        wechatPayment.setBody(body);
        wechatPayment.setOutTradeNo(orderNumber);
        wechatPayment.setTotalFee(orderFee);
        wechatPayment.setSpbillCreateIp(ip);
        wechatPayment.setTradeType(tradeType);
        wechatPayment.setProductId(productId);
        wechatPayment.setOpenId(openId);
        wechatPayment.setNotifyUrl(payNotifyUrl);

        return initPayment(wechatPayment);
    }

    private WechatPaymentResult initPayment(WechatPayment wechatPayment) throws Exception {
        Map<String, Object> data = MapUtils.getMap(wechatPayment, WechatPayment.class);
        wechatPayment.setSign(WechatSignUtil.getSign(data, WechatConfigSecret.getWechatPaySecret()));

        String paymentXml = parsePaymentXml(wechatPayment);
        logger.debug("init payment to wechat start : {}", paymentXml);
        String result = OkHttpUtils.postString().content(paymentXml).build().execute().body().string();
        logger.debug("init payment to wechat result: {}", result);
        return mapper.readValue(result,WechatPaymentResult.class);
    }

    private String parsePaymentXml(WechatPayment wechatPayment) {
        xStream.aliasField("appid", WechatPayment.class, "appId");
        xStream.aliasField("mch_id", WechatPayment.class, "mchId");
        xStream.aliasField("device_info", WechatPayment.class, "deviceInfo");
        xStream.aliasField("nonce_str", WechatPayment.class, "nonceStr");
        xStream.aliasField("sign_type", WechatPayment.class, "signType");
        xStream.aliasField("out_trade_no", WechatPayment.class, "outTradeNo");
        xStream.aliasField("total_fee", WechatPayment.class, "totalFee");
        xStream.aliasField("spbill_create_ip", WechatPayment.class, "spbillCreateIp");
        xStream.aliasField("notify_url", WechatPayment.class, "notifyUrl");
        xStream.aliasField("trade_type", WechatPayment.class, "tradeType");
        xStream.aliasField("product_id", WechatPayment.class, "productId");
        xStream.aliasField("openid", WechatPayment.class, "openId");

        return xStream.toXML(wechatPayment);
    }
}
