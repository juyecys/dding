package cn.com.dingduoduo.entity.wechat.pay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jeysine on 2018/4/19.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WechatPayment {
    @JsonProperty("appid")
    private String appId;

    @JsonProperty("mch_id")
    private String mchId;

    @JsonProperty("device_info")
    private String deviceInfo = "WEB";

    @JsonProperty("nonce_str")
    private String nonceStr;

    @JsonProperty("sign")
    private String sign;

    @JsonProperty("sign_type")
    private String signType = "MD5";

    @JsonProperty("body")
    private String body;

    @JsonProperty("detail")
    private String detail;

    @JsonProperty("attach")
    private String attach;

    @JsonProperty("out_trade_no")
    private String outTradeNo;

    @JsonProperty("total_fee")
    private Integer totalFee;

    @JsonProperty("spbill_create_ip")
    private String spbillCreateIp;

    @JsonProperty("time_start")
    private String timeStart;

    @JsonProperty("time_expire")
    private String timeExpire;

    @JsonProperty("goods_tag")
    private String goodsTag;

    @JsonProperty("notify_url")
    private String notifyUrl;

    @JsonProperty("trade_type")
    private String tradeType;

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("openid")
    private String openId;

    public static enum TradeTypeEnum {
        JSAPI, NATIVE, APP
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
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

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
    }

    public String getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
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
        return "WechatPay{" +
                "appId='" + appId + '\'' +
                ", mchId='" + mchId + '\'' +
                ", deviceInfo='" + deviceInfo + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", sign='" + sign + '\'' +
                ", signType='" + signType + '\'' +
                ", body='" + body + '\'' +
                ", detail='" + detail + '\'' +
                ", attach='" + attach + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", totalFee=" + totalFee +
                ", spbillCreateIp='" + spbillCreateIp + '\'' +
                ", timeStart='" + timeStart + '\'' +
                ", timeExpire='" + timeExpire + '\'' +
                ", goodsTag='" + goodsTag + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", productId='" + productId + '\'' +
                ", openId='" + openId + '\'' +
                '}';
    }
}
