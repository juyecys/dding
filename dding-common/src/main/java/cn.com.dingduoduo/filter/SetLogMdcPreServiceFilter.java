package cn.com.dingduoduo.filter;

import cn.com.dingduoduo.contants.SystemConstants;
import com.alibaba.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class SetLogMdcPreServiceFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(SetLogMdcPreServiceFilter.class);

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		String logId = RpcContext.getContext().getAttachment(SystemConstants.TRACE_ID);
		MDC.put(SystemConstants.TRACE_ID, logId);
		logger.trace("set traceid {} in SetLogMdcPreServiceFilter", logId);
		return invoker.invoke(invocation);
	}
}
