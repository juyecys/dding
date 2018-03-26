package cn.com.dingduoduo.entity.wechat.jssign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jeysine on 2018/3/26.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WechatJsSign {
    @JsonProperty("url")
    private String url;

    @JsonProperty("jsapiTicket")
    private String jsapiTicket;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("appId")
    private String appId;

    @JsonProperty("signature")
    private String signature;

    @JsonProperty("nonceStr")
    private String nonceStr;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJsapiTicket() {
        return jsapiTicket;
    }

    public void setJsapiTicket(String jsapiTicket) {
        this.jsapiTicket = jsapiTicket;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    @Override
    public String toString() {
        return "WechatJsSign{" +
                "url='" + url + '\'' +
                ", jsapiTicket='" + jsapiTicket + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", appId='" + appId + '\'' +
                ", signature='" + signature + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                '}';
    }
}
