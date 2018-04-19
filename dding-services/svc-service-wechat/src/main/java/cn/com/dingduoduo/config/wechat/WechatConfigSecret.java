package cn.com.dingduoduo.config.wechat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by jeysine on 2018/1/24.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.ALWAYS)
public class WechatConfigSecret {

    private static String wechatAppid;

    private static String wechatSecret;

    /**
     * 商户号
     */
    private static String wechatMchId;

    /**
     * 支付密钥
     */
    private static String wechatPaySecret;

    public void setWechatAppid(String wechatAppid) {
        this.wechatAppid = wechatAppid;
    }

    public void setWechatSecret(String wechatSecret) {
        this.wechatSecret = wechatSecret;
    }

    public static String getWechatAppid() {
        return wechatAppid;
    }

    public static String getWechatSecret() {
        return wechatSecret;
    }

    public static String getWechatMchId() {
        return wechatMchId;
    }

    public void setWechatMchId(String wechatMchId) {
        WechatConfigSecret.wechatMchId = wechatMchId;
    }

    public static String getWechatPaySecret() {
        return wechatPaySecret;
    }

    public void setWechatPaySecret(String wechatPaySecret) {
        WechatConfigSecret.wechatPaySecret = wechatPaySecret;
    }
}
