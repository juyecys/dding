package cn.com.dingduoduo.job;

import cn.com.dingduoduo.contants.SystemConstants;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;


/**
 * 分布式定时器运行时加入log
 * @author jeysine
 *
 */
@Aspect
@Component
public class SchedulerLogProcessAspect {

	private static Logger logger = LoggerFactory.getLogger(SchedulerLogProcessAspect.class);

	@Before("@annotation(org.springframework.scheduling.annotation.Scheduled) && within(cn.com.dingduoduo.job..*)")
	public void addTraceIdtoMDC(JoinPoint jp) throws Throwable {
		String traceid = UUID.randomUUID().toString();
		MDC.put(SystemConstants.TRACE_ID, traceid.toString());
		logger.trace("set traceid {} in SchedulerLogProcessAspect for {}", traceid, jp);
	}
}
