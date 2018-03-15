package cn.com.dingduoduo.entity.wechat.acesstoken;

import com.fasterxml.jackson.annotation.JsonProperty;
import cn.com.dingduoduo.entity.wechat.result.WechatCommonResult;

import java.io.Serializable;

/**
 * 调用微信接口所需的accesstoken
 */
public class WechatAccessToken extends WechatCommonResult implements Serializable{

	private static final long serialVersionUID = -7199898984637839010L;
	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("expires_in")
	private Long expiresIn;

	public WechatAccessToken() {
	}

	public WechatAccessToken(String accessToken, Long expiresIn) {
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

	@Override
	public String toString() {
		return "WechatAccessToken{" +
				"accessToken='" + accessToken + '\'' +
				", expiresIn=" + expiresIn +
				'}';
	}
}
