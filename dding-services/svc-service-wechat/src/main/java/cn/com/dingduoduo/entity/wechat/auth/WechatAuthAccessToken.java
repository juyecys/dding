package cn.com.dingduoduo.entity.wechat.auth;

import cn.com.dingduoduo.entity.wechat.acesstoken.WechatAccessToken;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by jeysine on 2018/2/8.
 */
public class WechatAuthAccessToken extends WechatAccessToken implements Serializable {

    private static final long serialVersionUID = 554601114357964177L;
    /**
     * 用户刷新accesstoken
     */
    @JsonProperty("refresh_token")
    private String refreshToken;

    /**
     * 用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
     */
    @JsonProperty("openid")
    private String openId;

    /**
     * 用户授权的作用域，使用逗号（,）分隔
     */
    @JsonProperty("scope")
    private String scope;

    @JsonProperty("unionid")
    private String unionId;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    @Override
    public String toString() {
        return super.toString() +  "WechatAuthAccessToken{" +
                "refreshToken='" + refreshToken + '\'' +
                ", openId='" + openId + '\'' +
                ", scope='" + scope + '\'' +
                ", unionId='" + unionId + '\'' +
                '}';
    }
}
