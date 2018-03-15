package cn.com.dingduoduo.entity.common;

import java.util.HashMap;
import java.util.Map;

public class Event {
	private String type;
	private Map<String, Object> eventProperties = new HashMap<String, Object>();
	public Event(String type) {
		super();
		this.type = type;
	}

	public Event() {
		super();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, Object> getProperties() {
		return eventProperties;
	}

	/**
	 * 
	 * @param key
	 * @param value
	 *            Must be primitive types like String, or boxed types, like
	 *            Integer, Long and etc.
	 */
	public void addProperty(String key, Object value) {
		eventProperties.put(key, value);
	}

	@Override
	public String toString() {
		return "Event [type=" + type + ", eventDetails=" + eventProperties
				+ "]";
	}
}
