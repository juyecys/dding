package cn.com.dingduoduo.jedis;

import redis.clients.jedis.Jedis;

public interface JedisCallback<T> {
	public T doWithJedis(Jedis jedis) throws JedisAccessException;
}
