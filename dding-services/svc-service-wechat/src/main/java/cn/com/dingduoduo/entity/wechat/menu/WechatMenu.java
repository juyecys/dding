package cn.com.dingduoduo.entity.wechat.menu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 用于向微信请求
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY )
public class WechatMenu{

	@JsonProperty("button")
	private WechatButton[] button;

	@JsonInclude(JsonInclude.Include.NON_EMPTY )
	public class WechatButton {
		@JsonProperty("name")
		private String name;

		@JsonProperty("sub_button")
		private WechatButton[] subButton;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public WechatButton[] getSubButton() {
			return subButton;
		}

		public void setSubButton(WechatButton[] subButton) {
			this.subButton = subButton;
		}
	}


	@JsonInclude(JsonInclude.Include.NON_EMPTY )
	public class WechatClickButton extends WechatButton {
		@JsonProperty("type")
		private String type;

		@JsonProperty("key")
		private String key;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}
	}

	@JsonInclude(JsonInclude.Include.NON_EMPTY )
	public class WechatMiniprogramButton extends WechatButton{

		/**
		 * miniprogram
		 */
		@JsonProperty("type")
		private String type;

		/**
		 * 小程序备用网址,设备不支持小程序跳转链接
		 */
		@JsonProperty("url")
		private String url;

		/**
		 * 小程序路径
		 */
		@JsonProperty("pagepath")
		private String pagePath;

		/**
		 * 小程序appid
		 */
		@JsonProperty("name")
		private String appId;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getPagePath() {
			return pagePath;
		}

		public void setPagePath(String pagePath) {
			this.pagePath = pagePath;
		}

		public String getAppId() {
			return appId;
		}

		public void setAppId(String appId) {
			this.appId = appId;
		}
	}

	@JsonInclude(JsonInclude.Include.NON_EMPTY )
	public class WechatViewButton extends WechatButton{
		@JsonProperty("type")
		private String type;

		@JsonProperty("url")
		private String url;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}

	public WechatButton[] getButton() {
		return button;
	}

	public void setButton(WechatButton[] button) {
		this.button = button;
	}
}
