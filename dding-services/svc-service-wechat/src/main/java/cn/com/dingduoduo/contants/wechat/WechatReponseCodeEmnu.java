package cn.com.dingduoduo.contants.wechat;

public enum WechatReponseCodeEmnu {
	OK(0, "ok"),
	SYSTEM_BUSY(-1, "cn.com.dingduoduo.api.admin.wechat system busy"),
	INVALID_APPSECRET(40001, "invalid app secret"),
	INVALID_OPENID(40003, "invalid openid"),
	INVALID_MEDIA_FILE_KIND(40004, "invalid media file kind"),
	INVALID_FILE_KIND(40005, "invalid file kind"),
	INVALID_FILE_SIZE(40006, "invalid file size"),
	INVALID_MEDIA_FILE_ID(40007, "invalid media file id"),
	INVALID_MESSAGE_KIND(40008, "invalid cn.com.dingduoduo.api.admin.adminuser.message kind"),
	INVALID_PICTURE_SIZE(40009, "invalid picture file size"),
	INVALID_VOICE_SIZE(40010, "invalid voice file size"),
	INVALID_VEDIO_SIZE(40011, "invalid vedio file size"),
	INVALID_THUMBNAIL_SIZE(40012, "invalid thumbnail file size"),
	INVALID_APPID(40013, "invalid appid"),
	INVALID_ACCESSTOKEN(40014, "invalid accesstoken"),
	INVALID_MENU_KIND(40015, "invalid menu kind"),
	INVALID_BUTTON_AMOUNT(40016, "invalid button amount");



	private Integer code;
	private String msg;

	WechatReponseCodeEmnu(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsgByCode(Integer code) {
		for (WechatReponseCodeEmnu i : WechatReponseCodeEmnu.values()) {
			if (code.equals(i.getCode())) {
				return i.getMsg();
			}
		}
		return "unknown wehcat error";
	}
}
