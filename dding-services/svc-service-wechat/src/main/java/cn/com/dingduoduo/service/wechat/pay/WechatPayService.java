package cn.com.dingduoduo.service.wechat.pay;

import cn.com.dingduoduo.entity.wechat.pay.WechatInitPaymentResult;

/**
 * Created by jeysine on 2018/4/19.
 */
public interface WechatPayService {
    WechatInitPaymentResult initPayment(String deviceInfo, String body, String attach, String orderNumber, Integer orderFee, String ip,
                                        String tradeType, String productId, String openId) throws Exception;
}
