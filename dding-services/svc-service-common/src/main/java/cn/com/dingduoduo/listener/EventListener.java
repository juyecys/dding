package cn.com.dingduoduo.listener;

import cn.com.dingduoduo.entity.common.Event;

public interface EventListener {
	public String getId();
	public void handleEvent(Event event);
}
