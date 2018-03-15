package cn.com.dingduoduo.api.common;


import cn.com.dingduoduo.utils.common.DuplicateCodeVerificationUtil;

public class ApiCodes {

	public static final int STATUS_INVALID_MSISDN = 4001;
	public static final int STATUS_INVALID_MSISDN_CODE = 4002;
	public static final int STATUS_INVALID_REQUEST = 4003;
	public static final int STATUS_INVALID_MSISDN_SIGNATURE = 4004;
	public static final int STATUS_MSISDN_REGISTRED = 4005;
	public static final int STATUS_MSISDN_AUTH_FAILED = 4006;
	public static final int STATUS_INVALID_TOKEN = 4007;
	public static final int STATUS_EXPIRED_TOKEN = 4008;
	public static final int STATUS_WRONG_OLD_PASSWORD = 4009;
	public static final int STATUS_MSISDN_LOCKED = 4010;
	public static final int STATUS_FORBIDDEN = 4011;

	public static final int STATUS_UNKNOWN_USER_ERROR = 4020;

	public static final int STATUS_UNKNOWN_ERROR = 5000;
	public static final int STATUS_INTERNAL_ERROR = 5001;
	public static final int STATUS_BUSY = 5003;

	public static final int STATUS_MSISDN_NOT_REGISTERED = 4124;
	public static final int STATUS_ORDER_EXPIRED = 4125;
	public static final int STATUS_ALREADY_PAID = 4126;
	public static final int STATUS_NO_ORDER_FOUND = 4127;
	public static final int STATUS_PAY_ORDER_TIME_OUT = 4128;// 支付超时
	public static final int STATUS_HAS_PAY = 4129;// 支付超时

	/* application codes */
	public static final int STATUS_INVALID_PUSH_TOKEN = 4100;
	public static final int STATUS_NO_APP_INFO_FOUND = 4110;
	public static final int STATUS_UPGRADE_NOT_NEEDED = 4111;

	public static final int STATUS_STORE_OSS_OBJ_FAILURE = 4200;
	public static final int STATUS_FILE_NOT_FOUND = 4201;
	public static final int STATUS_IO_ERROR = 4202;

	public static final int STATUS_OK = 2000;
	public static final int STATUS_NOT_FOUND = 4444;
	public static final int STATUS_COMMON_NOT_AUTHORIZED = 4445;// 没有对应权限(通用)

	/* 微信api的返回码 */

	public static final int STATUS_WXAPI_VALUE_IS_NOT_NULL = 45004; // 值不能为null
	public static final int STATUS_WXAPI_CONFIG_ERROR = 45005; // 微信配置错误
	public static final int STATUS_WXAPI_PHONE_IS_REGISTER = 45007; // 手机已经注册
	public static final int STATUS_WXAPI_REPEAT_CREATE_ORDER = 45010;// 重复下单
	public static final int STATUS_WXAPI_VERIFYCODE_OVERTIME = 45011;// 验证码超时
	public static final int STATUS_WXAPI_REGISTER_ERROR = 45014; // 注册失败
	public static final int STATUS_WXAPI_REGISTER_IMG_CODE_ERROR = 45015; // 图文验证码错误
	public static final int STATUS_WXAPI_WX_REGISTER_GET_VERIFYCODE_IP_NUM_ERROR = 45016; // 调用验证码ip次数
	public static final int STATUS_WXAPI_WX_REGISTER_RECOMMAND_CODE_ERROR = 45017; // 调用验证码ip次数
	public static final int STATUS_INVALID_PARAMETER = 45021; // 无效参数
	public static final int STATUS_DOCTOR_LUCK_DRAW_NOT_OPEN = 45022; //
	public static final int STATUS_DATA_EXISTING = 45023;// 已存在
	public static final int STATUS_DATA_NOT_FOUND = 45024;// 已存在
	public static final int STATUS_WXAPI_VERIFYCODE_TOOQUICK = 45055;// 验证码在一段时间内请求过频
	public static final int STATUS_WXAPI_VERIFYCODE_ERROR = 45056;// 验证码错误
	public static final int STATUS_WXAPI_NO_ATTENTION_CYS = 45057;// 未关注橙医生公众号


	public static final int STATUS_WXAPI_FAMILY_SAME_NAME = 45028; // 添加家庭联系人数量限制
	public static final int STATUS_WXAPI_INIT_PAYMENT_FAIL = 45029; // 发起支付失败
	public static final int STATUS_WXAPI_WX_PAYMENT_FAIL = 45030; // 微信支付失败
	public static final int STATUS_WXAPI_ORDER_ALREADY_PAID = 45031; // 订单已支付
	public static final int STATUS_WXAPI_ORDER_PAYMENT_TIMEOUT = 45032; // 订单超时未支付
	public static final int STATUS_WXAPI_ORDER_UPDATE_FAIL = 45033; // 订单修改出错


	/* reserve 4540x for examination sharing use */

	public static final int STATUS_JWT_SIGNATURE_INVALID = 4038; // JWT 签名有误
	public static final int STATUS_JWT_INVALID = 4039; // JWT 参数有误
	public static final int STATUS_JWT_EXPIRED = 4040; // JWT 过期

	/** Mini Program **/
	public static final int STATUS_INVALID_JS_CODE = 48000;// 小程序登录换取的Code有误
	public static final int STATUS_INVALID_SIGNATURE = 48001;// 签名有误
	public static final int STATUS_REGISTER_FAILED = 48002;// 小程序账户注册失败
	public static final int STATUS_USERINFO_UPDATE_FAILED = 48003;// 小程序账户信息更新失败

	// 必须在所有public static final int之后
	// Java 的static初始化顺序先初始化常量，再调用static block
	static {
		DuplicateCodeVerificationUtil.assertNoDuplicatePulicStaticFinalInt(ApiCodes.class);
	}

	private ApiCodes() {
		super();
	}
}
