package cn.com.dingduoduo.entity.wechat.pay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jeysine on 2018/4/20.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WechatPayment{
    @JsonProperty("timeStamp")
    private String timeStamp;

    @JsonProperty("package")
    private String packageStr;

    @JsonProperty("paySign")
    private String paySign;

    @JsonProperty("appid")
    private String appid;

    @JsonProperty("signType")
    private String signType;

    @JsonProperty("nonceStr")
    private String nonceStr;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPackageStr() {
        return packageStr;
    }

    public void setPackageStr(String packageStr) {
        this.packageStr = packageStr;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
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
        return "WechatPayment{" +
                "timeStamp='" + timeStamp + '\'' +
                ", packageStr='" + packageStr + '\'' +
                ", paySign='" + paySign + '\'' +
                ", appid='" + appid + '\'' +
                ", signType='" + signType + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                '}';
    }
}
