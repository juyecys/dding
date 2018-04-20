package cn.com.dingduoduo.service.wechat.pay;

import cn.com.dingduoduo.entity.wechat.pay.WechatPayment;

import java.math.BigDecimal;

/**
 * Created by jeysine on 2018/4/19.
 */
public interface WechatPayService {
    WechatPayment initPayment(String deviceInfo, String body, String orderNumber, BigDecimal orderFee, String ip,
                              String tradeType, String productId, String openId) throws Exception;
}
