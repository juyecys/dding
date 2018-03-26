package cn.com.dingduoduo.entity.wechat.jsticket;

import cn.com.dingduoduo.entity.wechat.result.WechatCommonResult;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jeysine on 2018/3/26.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WechatJsTicket extends WechatCommonResult {
    @JsonProperty("ticket")
    private String ticket;

    @JsonProperty("expires_in")
    private Long expiresIn;

    public WechatJsTicket() {
    }

    public WechatJsTicket(String ticket, Long expiresIn) {
        this.ticket = ticket;
        this.expiresIn = expiresIn;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public String toString() {
        return super.toString() + "WechatJsTicket{" +
                "ticket='" + ticket + '\'' +
                ", expiresIn=" + expiresIn +
                '}';
    }
}
