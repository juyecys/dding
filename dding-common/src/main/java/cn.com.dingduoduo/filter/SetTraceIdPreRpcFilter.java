package cn.com.dingduoduo.filter;

import cn.com.dingduoduo.contants.SystemConstants;
import com.alibaba.dubbo.rpc.*;
import org.slf4j.MDC;

public class SetTraceIdPreRpcFilter implements Filter {
	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		invocation.getAttachments().put(SystemConstants.TRACE_ID, MDC.get(SystemConstants.TRACE_ID));

		return invoker.invoke(invocation);
	}
}
