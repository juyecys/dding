package cn.com.dingduoduo.service.wechat.pay.impl;

import cn.com.dingduoduo.config.wechat.WechatConfigSecret;
import cn.com.dingduoduo.entity.wechat.pay.WechatPayment;
import cn.com.dingduoduo.service.wechat.pay.WechatPayService;
import cn.com.dingduoduo.untils.wechat.WechatSignUtil;
import cn.com.dingduoduo.utils.common.MapUtils;
import cn.com.dingduoduo.utils.common.XStreamUtil;
import cn.com.dingduoduo.utils.common.okhttputil.OkHttpUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
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

    @Override
    public WechatPayment initPayment(String deviceInfo, String body, String attach, String orderNumber, Integer orderFee, String ip,
                                     String tradeType, String productId, String openId) throws JAXBException, IOException, IllegalAccessException {
        WechatPayment wechatPayment = new WechatPayment();
        wechatPayment.setAppId(WechatConfigSecret.getWechatAppid());
        wechatPayment.setMchId(WechatConfigSecret.getWechatMchId());
        wechatPayment.setNonceStr(UUID.randomUUID().toString());

        wechatPayment.setDeviceInfo(deviceInfo);
        wechatPayment.setBody(body);
        wechatPayment.setAttach(attach);
        wechatPayment.setOutTradeNo(orderNumber);
        wechatPayment.setTotalFee(orderFee);
        wechatPayment.setSpbillCreateIp(ip);
        wechatPayment.setTradeType(tradeType);
        wechatPayment.setProductId(productId);
        wechatPayment.setOpenId(openId);
        wechatPayment.setNotifyUrl(payNotifyUrl);

        Map<String, Object> data = MapUtils.getMap(wechatPayment, WechatPayment.class);
        wechatPayment.setSign(WechatSignUtil.getSign(data, WechatConfigSecret.getWechatPaySecret()));

        String paymentXml = XStreamUtil.getxStream().toXML(wechatPayment);

        String result = OkHttpUtils.postString().content(paymentXml).build().execute().body().string();
        return wechatPayment;
    }

    private String parseXml(WechatPayment wechatPayment) {
        XStream xStream = new XStream(new DomDriver());
        //xStream.aliasField();
        return null;
    }
}
