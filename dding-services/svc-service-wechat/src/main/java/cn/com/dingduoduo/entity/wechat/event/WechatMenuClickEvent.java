package cn.com.dingduoduo.entity.wechat.event;

/**
 * Created by jeysine on 2018/1/24.
 */
public class WechatMenuClickEvent extends WechatBaseEvent {
    private String eventKey;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }
}
