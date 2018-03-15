package cn.com.dingduoduo.service.event;

import cn.com.dingduoduo.entity.common.Event;
import cn.com.dingduoduo.listener.EventListener;

public interface EventService {
	public void publish(Event event) throws EventServiceException;
	public void subscribe(String eventType, EventListener listener) throws EventServiceException;
}
