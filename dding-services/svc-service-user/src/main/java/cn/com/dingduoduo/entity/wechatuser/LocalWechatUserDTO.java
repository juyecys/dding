package cn.com.dingduoduo.entity.wechatuser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * Created by jeysine on 2018/1/26.
 */
@Alias("LocalWechatUserQM")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LocalWechatUserDTO extends LocalWechatUser{

    @JsonProperty("channels")
    private String channels;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonProperty("subscribe_date_start")
    private Date subscribeDateStart;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonProperty("subscribe_date_end")
    private Date subscribeDateEnd;

    @JsonProperty("channel_group_name")
    private String channelGroupName;

    public String getChannels() {
        return channels;
    }

    public void setChannels(String channels) {
        this.channels = channels;
    }

    public Date getSubscribeDateStart() {
        return subscribeDateStart;
    }

    public void setSubscribeDateStart(Date subscribeDateStart) {
        this.subscribeDateStart = subscribeDateStart;
    }

    public Date getSubscribeDateEnd() {
        return subscribeDateEnd;
    }

    public void setSubscribeDateEnd(Date subscribeDateEnd) {
        this.subscribeDateEnd = subscribeDateEnd;
    }

    public String getChannelGroupName() {
        return channelGroupName;
    }

    public void setChannelGroupName(String channelGroupName) {
        this.channelGroupName = channelGroupName;
    }

    @Override
    public String toString() {
        return super.toString() + "LocalWechatUserDTO{" +
                "channels='" + channels + '\'' +
                ", channelGroupName='" + channelGroupName + '\'' +
                '}';
    }
}
