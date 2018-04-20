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
    private String appid;

    @JsonProperty("mch_id")
    private String mch_id;

    @JsonProperty("trade_type")
    private String trade_type;

    @JsonProperty("sign")
    private String sign;

    @JsonProperty("sign_type")
    private String sign_type = "MD5";

    @JsonProperty("nonce_str")
    private String nonce_str;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    @Override
    public String toString() {
        return "WechatBasePayment{" +
                "appid='" + appid + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", trade_type='" + trade_type + '\'' +
                ", sign='" + sign + '\'' +
                ", sign_type='" + sign_type + '\'' +
                ", nonce_str='" + nonce_str + '\'' +
                '}';
    }
}
