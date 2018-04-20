package cn.com.dingduoduo.exception;

public class WechatPublicException extends Exception{
    public static enum WechatPublicErrorCode {
        SUCCESS(2000, "success"),
        ERROR(5000, "系统繁忙，请稍后重试"),
        PERMISSION_DENIED(4001, "请关注公众号"),
        ERROR_PARAMETER(5002, "参数错误"),
        NOT_EXITST_ORDER(5003, "订单不存在"),
        ERROR_IDCARD(5004, "身份证号码不正确"),
        ERROR_PHONE_NUMBER(5005, "手机号码不正确");

        private int code;
        private String desc;

        WechatPublicErrorCode(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    private WechatPublicErrorCode errorCode;

    public WechatPublicException() {
    }
    public WechatPublicException(WechatPublicErrorCode errorCode) {
        super(errorCode.getDesc());
        this.errorCode = errorCode;
    }

    public WechatPublicException(WechatPublicErrorCode errorCode, Exception e) {
        super(e);
        this.errorCode = errorCode;
    }
    public WechatPublicException(WechatPublicErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public WechatPublicException(String message) {
        super(message);
    }

    public WechatPublicException(Throwable cause) {
        super(cause);
    }

    public WechatPublicException(String message, Throwable cause) {
        super(message, cause);
    }

    public WechatPublicException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public WechatPublicErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(WechatPublicErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
