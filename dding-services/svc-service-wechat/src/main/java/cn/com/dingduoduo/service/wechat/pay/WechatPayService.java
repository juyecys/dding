package cn.com.dingduoduo.service.wechat.pay;

import cn.com.dingduoduo.entity.wechat.pay.WechatPayment;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * Created by jeysine on 2018/4/19.
 */
public interface WechatPayService {
    WechatPayment initPayment(String deviceInfo, String body, String attach, String orderNumber, Integer orderFee, String ip,
                              String tradeType, String productId, String openId) throws JAXBException, IOException, IllegalAccessException;
}
