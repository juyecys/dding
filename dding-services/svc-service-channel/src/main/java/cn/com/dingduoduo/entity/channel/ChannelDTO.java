package cn.com.dingduoduo.entity.channel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

@Alias("ChannelQM")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ChannelDTO extends Channel {
    @JsonProperty("channel_group_name")
    private String channelGroupName;

    public String getChannelGroupName() {
        return channelGroupName;
    }

    public void setChannelGroupName(String channelGroupName) {
        this.channelGroupName = channelGroupName;
    }

    @Override
    public String toString() {
        return "ChannelDTO{" +
                "channelGroupName='" + channelGroupName + '\'' +
                '}';
    }
}
