package cn.com.dingduoduo.api.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class ApiResult {
	private final int code;
	private final String desc;
	private Object result;
	@JsonProperty("extra_info")
	private Object extraInfo;

	public ApiResult(int code, String description, Object result, Object extraInfo) {
		super();
		this.code = code;
		this.desc = description;
		this.result = result;
		this.extraInfo = extraInfo;
	}

	public ApiResult(int code, String description, Object result) {
		super();
		this.code = code;
		this.desc = description;
		this.result = result;
	}

	public ApiResult(int code, String description) {
		super();
		this.code = code;
		this.desc = description;
		this.result = null;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public Object getResult() {
		return result;
	}

	@Override
	public String toString() {
		return "BizError [status=" + code + ", description=" + desc + ", result="
				+ (result == null ? null : result.toString()) + "]";
	}

	public static ApiResult build(int status, String description, Object result) {
		return new ApiResult(status, description, result);
	}

	public static ApiResult build(int status, String description) {
		return new ApiResult(status, description);
	}

	public static ApiResult error(int status) {
		return new ApiResult(status, "error");
	}

	public static ApiResult error(int status, Object result) {
		return new ApiResult(status, "error", result);
	}

	public static ApiResult success() {
		return new ApiResult(ApiCodes.STATUS_OK, "success");
	}

	public static ApiResult success(Object result) {
		return new ApiResult(ApiCodes.STATUS_OK, "success", result);
	}

	public static ApiResult success(Object result, Object extraInfo) {
		return new ApiResult(ApiCodes.STATUS_OK, "success", result, extraInfo);
	}

	public static ApiResult unknownError() {
		return new ApiResult(ApiCodes.STATUS_UNKNOWN_USER_ERROR, "Unknown error.");
	}
}