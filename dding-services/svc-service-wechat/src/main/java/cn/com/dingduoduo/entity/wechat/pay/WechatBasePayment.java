package cn.com.dingduoduo.entity.wechat.pay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jeysine on 2018/4/20.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WechatBasePayment {
    @JsonProperty("appid")
    private String appId;

    @JsonProperty("mch_id")
    private String mchId;

    @JsonProperty("trade_type")
    private String tradeType;

    @JsonProperty("sign")
    private String sign;

    @JsonProperty("sign_type")
    private String signType = "MD5";

    @JsonProperty("nonce_str")
    private String nonceStr;

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

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
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

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    @Override
    public String toString() {
        return "WechatBasePayment{" +
                "appId='" + appId + '\'' +
                ", mchId='" + mchId + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", sign='" + sign + '\'' +
                ", signType='" + signType + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                '}';
    }
}
