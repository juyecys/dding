package cn.com.dingduoduo.job;

import cn.com.dingduoduo.jedis.JedisAccessException;
import cn.com.dingduoduo.jedis.JedisCallback;
import cn.com.dingduoduo.jedis.JedisTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.UUID;

/**
 * 用于选举一个leader
 * @author jeysine
 *
 */
public class LeaderElector {

	private static final String CYS_LEADER_ELECTOR = "architecture-leader-elector:";
	private String leaderRedisKey;
	private static Logger logger = LoggerFactory.getLogger(LeaderElector.class);

	private JedisTemplate jedisTemplate;

	private String currentInstanceId;
	private Thread heartbeatThread;
	private String electorId;
	private volatile boolean isStopped;
	private volatile boolean isSendingHeartbeat = false;

	public void destroy() throws Exception {
		isStopped = true;
		// wake up from sleeping
		heartbeatThread.interrupt();
		heartbeatThread.join(1000l);
	}

	public LeaderElector(JedisTemplate jedisTemplate, String electorId) {
		this.electorId = electorId;
		this.jedisTemplate = jedisTemplate;
		this.leaderRedisKey = CYS_LEADER_ELECTOR + electorId;
		currentInstanceId = UUID.randomUUID().toString();
		logger.info("Current instance ID is {} for elector {}.", currentInstanceId, electorId);
		heartbeatThread = new Thread() {
			@Override
			public void run() {
				while (!isStopped) {
					try {
						isSendingHeartbeat = true;
						heartBeat();
						isSendingHeartbeat = false;
						Thread.sleep(heartbeatSeconds * 1000l);
					} catch (Exception e) {
						logger.warn("Caught exception while sending heartbeat.", e);
					} finally {
						isSendingHeartbeat = false;
					}
				}
				logger.info("Heartbeat stopped.");
			}
		};
		heartbeatThread.setName("Distributed-Schduler-Heartbeat-" + electorId);
		heartbeatThread.start();
	}

	private static int heartbeatSeconds = 15;

	protected void heartBeat() {
		jedisTemplate.execute(new JedisCallback<Void>() {
			@Override
			public Void doWithJedis(Jedis jedis) throws JedisAccessException {
				String currentMasterInstanceId = jedis.get(leaderRedisKey);
				if (!currentInstanceId.equals(currentMasterInstanceId) && currentMasterInstanceId != null) {
					logger.trace("Not leader: {} for {}", currentInstanceId, electorId);
					return null;
				} else if (currentMasterInstanceId == null) {
					// 要去抢Master,将leader的生效时间设得比heartbeat的时间长,在没有过期前就延长生命期
					// 如果成功，saveResult是OK
					String saveResult = jedis.set(leaderRedisKey, currentInstanceId, "NX", "EX", heartbeatSeconds * 2);
					if (saveResult != null) {
						logger.info("Successfully obtained leader key {} for elector {}", currentInstanceId, electorId);
					} else {
						// 没有抢到
						logger.trace("Not obtained leader key {} for elector {}", currentInstanceId, electorId);
					}
					return null;
				}

				// 本进程有可能是leader，尝试去延长key的生存时间
				// 确保expire命令执行成功，使用Redis的watch和transaction功能
				// 就算watch的key为空，watch会成功，但是如果key保持为空，下面的result检查会为0，一样可以知道非leader
				// 如果key被写入新值, 那么exec会返回null，同样可以知道非leader
				jedis.watch(leaderRedisKey);
				Transaction t = jedis.multi();
				// 将expire放置于transaction中，如果watch的key变化了（也就是被删了或者内容被改了），expire会被取消执行
				t.expire(leaderRedisKey, heartbeatSeconds * 2);
				List<Object> result = t.exec();
				if (result == null || ((Long) result.get(0)).equals(0l)) {
					// 说明上面的exec被取消了(result==null)因为PRIMARY_KEY内容被改变了，但不包括被超时了
					// result.get(0)==0l, 说明执行expire时key不存在了，被超时了
					logger.trace("Not leader anymore, heartbeat not sent {} for elector {}. Result is {}",
							currentInstanceId, electorId, result);
				} else {
					// 到达这里，key一定还存在，而且被延长了heartbeatSeconds * 2秒
					logger.trace("Elector {} is still leader for {}, heartbeat sent.", currentInstanceId, electorId);
				}
				return null;
			}
		});
	}

	public boolean isLeader() {
		return jedisTemplate.execute(new JedisCallback<Boolean>() {
			@Override
			public Boolean doWithJedis(Jedis jedis) throws JedisAccessException {
				String currentMasterInstanceId = jedis.get(leaderRedisKey);
				return currentInstanceId.equals(currentMasterInstanceId);
			}
		});
	}

	public boolean isSendingHeartbeat() {
		return isSendingHeartbeat;
	}

	@Override
	public String toString() {
		return "LeaderElector [electorId=" + electorId + "]";
	}
}
