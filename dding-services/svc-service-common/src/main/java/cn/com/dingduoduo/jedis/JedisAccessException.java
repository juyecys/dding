package cn.com.dingduoduo.jedis;

public class JedisAccessException extends RuntimeException {
	private static final long serialVersionUID = -2946264222682282677L;

	public JedisAccessException(String message) {
		super(message);
	}

	public JedisAccessException(Throwable e) {
		super(e);
	}

	public JedisAccessException(String message, Throwable cause) {
		super(message, cause);
	}
}
