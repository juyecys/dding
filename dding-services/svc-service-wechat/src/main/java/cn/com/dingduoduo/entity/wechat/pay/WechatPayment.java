package cn.com.dingduoduo.entity.wechat.pay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jeysine on 2018/4/20.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WechatPayment extends WechatBasePayment {
    @JsonProperty("timeStamp")
    private String timeStamp;

    @JsonProperty("package")
    private String packageStr;

    @JsonProperty("paySign")
    private String paySign;

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

    @Override
    public String toString() {
        return super.toString() + "WechatPayment{" +
                "timeStamp='" + timeStamp + '\'' +
                ", packageStr='" + packageStr + '\'' +
                ", paySign='" + paySign + '\'' +
                '}';
    }
}
