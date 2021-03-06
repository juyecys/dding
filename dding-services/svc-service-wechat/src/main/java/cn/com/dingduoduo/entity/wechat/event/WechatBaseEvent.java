package cn.com.dingduoduo.entity.wechat.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by jeysine on 2018/1/23.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WechatBaseEvent {

    private String toUserName;

    private String fromUserName;

    private Long createTime;

    private String msgType;

    private String event;

    public static enum EventEnum {
        SUBSCRIBE("subscribe"), UN_SUBSCRIBE("unsubscribe"), SCAN("SCAN"), LOCATION("LOCATION"),
        CLICK("CLICK"), VIEW("VIEW");

        private String value ;

        private EventEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static EventEnum getEnumByValue(String value) {
            for (EventEnum e: EventEnum.values()) {
                if (e.getValue().equals(value)) {
                    return e;
                }
            }
            return null;
        }
    }

    public static enum MsgTypeEnum {
        EVENT("event"),
        TEXT("text");

        private String value ;

        private MsgTypeEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static MsgTypeEnum getEnumByValue(String value) {
            for (MsgTypeEnum e: MsgTypeEnum.values()) {
                if (value.equals(e.getValue())) {
                    return e;
                }
            }
            return null;
        }
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }


    @Override
    public String toString() {
        return "WechatBaseEvent{" +
                "toUserName='" + toUserName + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", createTime=" + createTime +
                ", msgType='" + msgType + '\'' +
                ", event='" + event + '\'' +
                '}';
    }
}
