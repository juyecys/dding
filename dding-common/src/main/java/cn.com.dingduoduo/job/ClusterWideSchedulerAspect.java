package cn.com.dingduoduo.job;

import cn.com.dingduoduo.jedis.JedisTemplate;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class ClusterWideSchedulerAspect implements ApplicationContextAware {

	private static Logger logger = LoggerFactory.getLogger(ClusterWideSchedulerAspect.class);
	private ApplicationContext applicationContext;
	private Map<String, LeaderElector> leaderElectors = new HashMap<>();

	@Autowired
	private JedisTemplate jedisTemplate;

	@PreDestroy
	public void destroy() throws Exception {
		Collection<LeaderElector> values = leaderElectors.values();
		for (LeaderElector elector : values) {
			try {
				elector.destroy();
				logger.info("Destroyed {}", elector);
			} catch (Exception e) {
				// ignore
			}
		}
	}

	@PostConstruct
	public void init() {
		Map<String, Object> annotatedWithDistributedTask = applicationContext
				.getBeansWithAnnotation(DistributedExclusiveTask.class);
		Collection<Object> values = annotatedWithDistributedTask.values();
		for (Object object : values) {
			String timerId = object.getClass().getAnnotation(DistributedExclusiveTask.class).value();
			if (!leaderElectors.containsKey(timerId)) {
				LeaderElector elector = new LeaderElector(jedisTemplate, timerId);
				leaderElectors.put(timerId, elector);
				logger.info("Built {} for class {}", elector, object.getClass());
			}
		}
	}

	@Around("@target(cn.com.dingduoduo.job.DistributedExclusiveTask) && within(cn.com.dingduoduo.job..*)")
	public void decideWhetherMasterAndProceed(ProceedingJoinPoint pjp) throws Throwable {
		logger.trace("Intercepted: " + pjp);

		String timerId = pjp.getTarget().getClass().getAnnotation(DistributedExclusiveTask.class).value();
		LeaderElector elector = leaderElectors.get(timerId);
		if (elector == null) {
			logger.info("Doesn't find an elector for {} with timer id {}.", pjp, timerId);
			return;
		}
		long start = System.currentTimeMillis();

		while (elector.isSendingHeartbeat()) {
			// 因为可能改变当前实例的主从状态，所以等heartbeat发完再执行
			logger.info("Sending heartbeat, try next round.");
			Thread.sleep(100l);
			if (System.currentTimeMillis() - start > 5000l) {
				logger.warn("Elector {} is sending heartbeat for too long time. {}", elector, pjp);
				return;
			}
		}

		if (elector.isLeader()) {
			pjp.proceed();
		} else {
			logger.trace("Elector {} not leader, skipping {}", elector, pjp);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
