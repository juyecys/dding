package cn.com.dingduoduo.entity.wechat.pay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jeysine on 2018/4/19.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WechatInitPayment extends WechatBasePayment {

    @JsonProperty("device_info")
    private String deviceInfo = "WEB";

    @JsonProperty("body")
    private String body;

    @JsonProperty("out_trade_no")
    private String outTradeNo;

    @JsonProperty("total_fee")
    private Integer totalFee;

    @JsonProperty("spbill_create_ip")
    private String spbillCreateIp;

    @JsonProperty("notify_url")
    private String notifyUrl;

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("openid")
    private String openId;

    public static enum TradeTypeEnum {
        JSAPI, NATIVE, APP
    }
    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        return "WechatInitPayment{" +
                "deviceInfo='" + deviceInfo + '\'' +
                ", body='" + body + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", totalFee=" + totalFee +
                ", spbillCreateIp='" + spbillCreateIp + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", productId='" + productId + '\'' +
                ", openId='" + openId + '\'' +
                '}';
    }
}
