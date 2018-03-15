package cn.com.dingduoduo.entity.channel;

import cn.com.dingduoduo.entity.common.Base;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

@Alias("ChannelM")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Channel extends Base {
    /**
     * 推广渠道
     */
    @JsonProperty("channels")
    private String channels;

    /**
     * 二维码地址
     */
    @JsonProperty("qr_code_url")
    private String qrCodeUrl;

    /***
     * 编码
     */
    @JsonProperty("scene")
    private String scene;

    @JsonProperty("ticket")
    private String ticket;

    @JsonProperty("send_channel_message")
    private Boolean sendChannelMessage;

    @JsonProperty("send_subscribe_message")
    private Boolean sendSubscribeMessage;

    @JsonProperty("channel_group_id")
    private String channelGroupId;

    public String getChannels() {
        return channels;
    }

    public void setChannels(String channels) {
        this.channels = channels;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Boolean getSendChannelMessage() {
        return sendChannelMessage;
    }

    public void setSendChannelMessage(Boolean sendChannelMessage) {
        this.sendChannelMessage = sendChannelMessage;
    }

    public Boolean getSendSubscribeMessage() {
        return sendSubscribeMessage;
    }

    public void setSendSubscribeMessage(Boolean sendSubscribeMessage) {
        this.sendSubscribeMessage = sendSubscribeMessage;
    }

    public String getChannelGroupId() {
        return channelGroupId;
    }

    public void setChannelGroupId(String channelGroupId) {
        this.channelGroupId = channelGroupId;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "channels='" + channels + '\'' +
                ", qrCodeUrl='" + qrCodeUrl + '\'' +
                ", scene='" + scene + '\'' +
                ", ticket='" + ticket + '\'' +
                ", sendChannelMessage=" + sendChannelMessage +
                ", sendSubscribeMessage=" + sendSubscribeMessage +
                ", channelGroupId='" + channelGroupId + '\'' +
                '}';
    }
}
