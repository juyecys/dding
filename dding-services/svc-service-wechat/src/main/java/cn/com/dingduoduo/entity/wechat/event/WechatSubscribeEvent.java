package cn.com.dingduoduo.entity.wechat.event;

/**
 * Created by jeysine on 2018/1/23.
 */
public class WechatSubscribeEvent extends WechatBaseEvent{
    private String eventKey;

    private String ticket;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
