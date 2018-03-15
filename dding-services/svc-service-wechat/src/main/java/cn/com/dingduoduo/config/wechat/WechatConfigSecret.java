package cn.com.dingduoduo.config.wechat;

/**
 * Created by jeysine on 2018/1/24.
 */
public class WechatConfigSecret {

    private static String wechatAppid;
    private static String wechatSecret;

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
}
