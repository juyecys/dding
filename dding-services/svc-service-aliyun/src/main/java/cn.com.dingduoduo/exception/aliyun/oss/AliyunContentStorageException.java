package cn.com.dingduoduo.exception.aliyun.oss;

public class AliyunContentStorageException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6622038044522141766L;

	public AliyunContentStorageException() {
		super();
	}

	public AliyunContentStorageException(String message, Throwable cause,
                                         boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AliyunContentStorageException(String message, Throwable cause) {
		super(message, cause);
	}

	public AliyunContentStorageException(String message) {
		super(message);
	}

	public AliyunContentStorageException(Throwable cause) {
		super(cause);
	}

}
