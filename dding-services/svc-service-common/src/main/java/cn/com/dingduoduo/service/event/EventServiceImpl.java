package cn.com.dingduoduo.service.event;

import cn.com.dingduoduo.entity.common.Event;
import cn.com.dingduoduo.jedis.JedisAccessException;
import cn.com.dingduoduo.jedis.JedisCallback;
import cn.com.dingduoduo.jedis.JedisTemplate;
import cn.com.dingduoduo.listener.EventListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class EventServiceImpl implements EventService {
	public final static String EVENT_QUEUE_PREFIX = "dding_eq:";
	public final static String EVENT_QUEUE_LISTENERS_PREFIX = "dding_eql:";
	private ObjectMapper mapper = new ObjectMapper();
	private Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);
	private Map<String, Map<String, EventListener>> eventTypeToListenersMap = new ConcurrentHashMap<>();
	private ExecutorService eventWorkerThreadPool = Executors.newFixedThreadPool(5);
	private Thread patrolThread;
    private int timeout;
    
    @Autowired
    private JedisTemplate jedisTemplate;

	private Object lock = new Object();

	private boolean isStopToScan = false;
	/**
	 * If set to true, this instance will not scan the event queue.
	 */
	private boolean isPurePublisherMode = false;

	@PostConstruct
	public void init() {
		if (!isPurePublisherMode) {
			initPatrolThread();
		} else {
			logger.warn("Event service is working purely in publisher mode. No event queue scanning will be started.");
		}
	}

	@PreDestroy
	public void stop() {
		isStopToScan = true;
		eventWorkerThreadPool.shutdown();
		try {
			eventWorkerThreadPool.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			//ignore
		}
	}

	private String buildEventQueueRedisKey(String eventType, String listenerId) {
		return EVENT_QUEUE_PREFIX + eventType + ":" + listenerId;
	}

	private String buildListenerRedisKey(String eventType) {
		return EVENT_QUEUE_LISTENERS_PREFIX + eventType;
	}

	private int scanEventQueues(Jedis jedis) {
		int counter = 0;
		Set<String> listenersKeys = jedis.keys(EVENT_QUEUE_LISTENERS_PREFIX
				+ "*");
		logger.debug("Found listener keys {}", listenersKeys);
		for (String listenerKey : listenersKeys) {
			String[] splitted = listenerKey.split(":");
			if (splitted.length < 2) {
				logger.warn("Found an invalid listener set key: {}",
						listenerKey);
				continue;
			}
			String eventType = splitted[1];
			//logger.debug("Found event type to scan {}", eventType);
			Map<String, EventListener> listeners = eventTypeToListenersMap
					.get(eventType);
			if (listeners == null) {
				//logger.debug("No listener registered for this event type {}", eventType);
				continue;
			}

			Set<String> eventQueueKeys = jedis.keys(EVENT_QUEUE_PREFIX
					+ eventType + "*");
			logger.trace("Found event queue keys {}", eventQueueKeys);
			for (String eventQueueKey : eventQueueKeys) {
				splitted = eventQueueKey.split(":");
				if (splitted.length < 3) {
					logger.warn("Found an invalid event queue key: {}",
							listenerKey);
					continue;
				}

				String listenerId = splitted[2];
				final EventListener listener = listeners.get(listenerId);
				if (listener != null) {
					String redisValue = jedis.lpop(eventQueueKey);
					logger.debug(
							"Checking key {} for event type {} and listener id {}, and got value {}",
							eventQueueKey, eventType, listenerId, redisValue);
					if (!StringUtils.isEmpty(redisValue)) {
						try {
							TimeStampedEvent eventWithTs = mapper.readValue(
									redisValue, TimeStampedEvent.class);
							final Event event = eventWithTs.event;
							counter++;
							eventWorkerThreadPool.execute(new Runnable() {
								@Override
								public void run() {
									logger.debug("Handling event {}", event);
									listener.handleEvent(event);
								}
							});
						} catch (IOException e) {
							logger.warn("Deserializing event failed", e);
							continue;
						}

					} else {
						// TODO it means the queue is empty
					}
				} else {
					logger.warn("No listener found for listener id {}",
							listenerId);
					// FIXME how to expire the event in queue
				}
			}
		}
		return counter;
	}
	
	private void sleepSilently(long msecs) {
		try {
			Thread.sleep(msecs);
		} catch (InterruptedException e) {
			//ignore
		}
	}

	private void initPatrolThread() {
		patrolThread = new Thread() {
			@Override
			public void run() {
				while (!isStopToScan) {
					// synchronized with the stop method
					// to avoid having a shutdown redis to scan
					synchronized (lock) {
						jedisTemplate.execute(new JedisCallback<Void>() {
							@Override
							public Void doWithJedis(Jedis jedis) throws JedisAccessException {
								int eventCount = scanEventQueues(jedis);
								if (eventCount == 0) {
									sleepSilently(5000l);
								}
								return null;
							}
						});

						/* Java 8 Lambda expression
						jedisTemplate.execute(jedis -> {
							int eventCount = scanEventQueues(jedis);
							if (eventCount == 0) {
								sleepSilently(5000l);
							}
							return null;
						});*/
					}

				} // while

				logger.info("Stopped to scan the keys");
			}

		};
		patrolThread.setName("Event-Service-Patrol-Thread");
		patrolThread.start();
	}

	/**
	 * FIXME The subscriber on every server MUST listen to all the event types
	 * that server will generate Otherwise, the event could be lost if no one
	 * listens to the event type.
	 */
	@Override
	public void publish(final Event event) throws EventServiceException {
		logger.debug("Publishing event {}", event);
		final String eventType = event.getType();
		if (StringUtils.isEmpty(eventType)) {
			throw new IllegalArgumentException("Can not pulish event with empty type!");
		}

		jedisTemplate.execute(new JedisCallback<Void>() {
			@Override
			public Void doWithJedis(Jedis jedis) throws JedisAccessException {
				try {
					Set<String> eventTypeListeners = jedis.smembers(buildListenerRedisKey(eventType));
					logger.debug("Found event type listeners: {}", eventTypeListeners);

					if (eventTypeListeners == null || eventTypeListeners.isEmpty()) {
						logger.warn("No listener for {}", eventType);
						return null;
					}

					final String serializedValue;
					TimeStampedEvent timedEvent = new TimeStampedEvent(System.currentTimeMillis(), event);
					serializedValue = mapper.writeValueAsString(timedEvent);
					logger.debug("Serialized the event to {}", serializedValue);
					for (String listenerId : eventTypeListeners) {
						String eventQueueKey = buildEventQueueRedisKey(eventType, listenerId);
						Long result = jedis.rpush(eventQueueKey, serializedValue);
						logger.debug("RPUSHED {} to key {} with result {}", serializedValue, eventQueueKey, result);
					}
				} catch (JsonProcessingException e) {
					logger.warn("Can not serialize the event to String", e);
					throw new JedisAccessException("Can not serialize the event to String", e);
				}
				return null;
			}
		});
	}

	@Override
	public void subscribe(final String eventType, final EventListener listener) throws EventServiceException {
		logger.trace("Adding listener {} for eventType {}", listener.getId(), eventType);

		jedisTemplate.execute(new JedisCallback<Void>() {
			@Override
			public Void doWithJedis(Jedis jedis) throws JedisAccessException {
				synchronized (eventTypeToListenersMap) {
					String redisListenerKey = buildListenerRedisKey(eventType);
					Long result = jedis.sadd(redisListenerKey, listener.getId());
					logger.debug("Added listener {} to redis {} and result {}", listener.getId(), redisListenerKey,
							result);
					Map<String, EventListener> listenersIdMap = eventTypeToListenersMap.get(eventType);
					if (listenersIdMap == null) {
						listenersIdMap = new HashMap<>();
						logger.debug("Not handled event type {}", eventType);
						eventTypeToListenersMap.put(eventType, listenersIdMap);
					}
					listenersIdMap.put(listener.getId(), listener);
				}
				return null;
			}
		});
	}

	public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
	    
	public void setPurePublisherMode(boolean isPurePublisherMode) {
		this.isPurePublisherMode = isPurePublisherMode;
	}

	/**
	 * Timestamp the event in order to clean up the queue
	 * 
	 *
	 */
	public static class TimeStampedEvent {
		public long timestamp;
		public Event event;

		public TimeStampedEvent(long timestamp, Event event) {
			super();
			this.timestamp = timestamp;
			this.event = event;
		}

		public TimeStampedEvent() {
			super();
		}

		@Override
		public String toString() {
			return "TimeStampedEvent [timestamp=" + timestamp + ", event="
					+ event + "]";
		}
	}

	public void setJedisTemplate(JedisTemplate jedisTemplate) {
		this.jedisTemplate = jedisTemplate;
	}
}
