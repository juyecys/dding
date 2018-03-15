package cn.com.dingduoduo.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

public class JedisTemplate {
	private JedisPool jedisPool;

	public <T> T execute(JedisCallback<T> callback) throws JedisAccessException {
		try (Jedis jedis = jedisPool.getResource()) {
			if (jedis == null) {
				throw new JedisException("No Redis Connection is available.");
			}

			T value = callback.doWithJedis(jedis);
			return value;
		} 

	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
}
